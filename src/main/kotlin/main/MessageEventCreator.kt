package main

import common.AppConstant
import common.LogHelper
import data.data_source.db.neo4j.model.Person
import domain.model.MessageDomain
import domain.model.PostDomain
import java.io.File
import java.io.FileWriter
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.PriorityQueue
import javax.inject.Inject

class MessageEventCreator @Inject constructor(

    private val messagesGenerator: MessagesGenerator

) {

    private val refreshTokensTime = arrayListOf<Pair<Date,Date>>()

    private lateinit var users: Map<Int, Person>

    private lateinit var startDate: Date

    private lateinit var endDate: Date

    private lateinit var statisticEventParams: StatisticEventParams

    private val comparator = Comparator<MessageEvent> { event1, event2 ->

        event1?.time?.time?.compareTo(event2?.time?.time!!) ?: -1
    }

    private var messageEvents = PriorityQueue(comparator)

    private lateinit var nextMessageTime: Date

    fun setUsers(users: Map<Int, Person>) {

        if (validateRelationships(users.values).not()) {

            LogHelper.logE("Wrong Social Model! No relationships between nodes!")
            return
        }

        this.users = users

        users.values.forEach { user ->

            user.actions.forEach { action ->

                val event = when (action.public) {

                    true -> MessageEvent.PublicPostEvent(

                        time = action.time,
                        message = PostDomain(

                            message = action.message,
                            senderId = user.diasporaId.orEmpty(),
                            aspect = user.aspectId!!
                        ),
                        token = user.authToken
                    )
                    false -> MessageEvent.ConversationMessageEvent(

                        time = action.time,
                        message = MessageDomain(

                            guid = user.relationshipIds[action.recipientId.toInt()]!!,
                            senderId = user.diasporaId.orEmpty(),
                            recipientId = users[action.recipientId.toInt()]?.diasporaId!!,
                            message = action.message
                        ),
                        token = user.authToken
                    )
                }

                messageEvents.offer(event)
            }
        }

        init()

        statisticEventParams = StatisticEventParams(users.count())

        val firstMessageCalendar = GregorianCalendar()
        firstMessageCalendar.time = startDate
        firstMessageCalendar.add(Calendar.MINUTE, (statisticEventParams.totalMessageEventCount * 0.045).toInt() / 60)
        nextMessageTime = firstMessageCalendar.time

        while (nextMessageTime.time < endDate.time) {

            createMessageEvents()
        }

        LogHelper.logD("Statistics:")
        LogHelper.logSeparator()
        LogHelper.logD("totalCount: ${messageEvents.count()}")
        LogHelper.logD("mondayCount: ${getDayCount(Calendar.MONDAY)}")
        LogHelper.logD("tuesdayCount: ${getDayCount(Calendar.TUESDAY)}")
        LogHelper.logD("wednesdayCount: ${getDayCount(Calendar.WEDNESDAY)}")
        LogHelper.logD("thursdayCount: ${getDayCount(Calendar.THURSDAY)}")
        LogHelper.logD("fridayCount: ${getDayCount(Calendar.FRIDAY)}")
        LogHelper.logD("saturdayCount: ${getDayCount(Calendar.SATURDAY)}")
        LogHelper.logD("sundayCount: ${getDayCount(Calendar.SUNDAY)}")

        LogHelper.logD("startDate: $startDate")
        LogHelper.logD("endDate: $endDate")

        LogHelper.logD("countConversations: ${messagesGenerator.personConversationPairs.count()}")
        LogHelper.logSeparator()

        val fileStatistics = File("/home/renat/Desktop/social network interaction/statistics.txt")
        val writerStatistics = FileWriter(fileStatistics, false)

        writerStatistics.appendln("Statistics:")
        writerStatistics.appendln(LogHelper.separator)
        writerStatistics.appendln("Users count: ${users.count()}")
        writerStatistics.appendln("Total event count: ${messageEvents.count()}")
        writerStatistics.appendln("Public posts count: ${messageEvents.filterIsInstance<MessageEvent.PublicPostEvent>().count()}")
        writerStatistics.appendln("Total private messages count: ${messageEvents.filterIsInstance<MessageEvent.ConversationMessageEvent>().count()}")
        writerStatistics.appendlnDayStat(Calendar.MONDAY)
        writerStatistics.appendlnDayStat(Calendar.TUESDAY)
        writerStatistics.appendlnDayStat(Calendar.WEDNESDAY)
        writerStatistics.appendlnDayStat(Calendar.THURSDAY)
        writerStatistics.appendlnDayStat(Calendar.FRIDAY)
        writerStatistics.appendlnDayStat(Calendar.SATURDAY)
        writerStatistics.appendlnDayStat(Calendar.SUNDAY)
        writerStatistics.close()
    }

    private fun FileWriter.appendlnDayStat(day: Int) {

        val dayHeader = when(day) {

            Calendar.MONDAY -> "Понедельник"
            Calendar.TUESDAY -> "Вторник"
            Calendar.WEDNESDAY -> "Среда"
            Calendar.THURSDAY -> "Четверг"
            Calendar.FRIDAY -> "Пятница"
            Calendar.SATURDAY -> "Суббота"
            Calendar.SUNDAY ->  "Воскресенье"
            else -> error("non supporting day value: $day")
        }

        appendln(LogHelper.separator)
        appendln("$dayHeader:")
        appendln("All: ${getDayCount(day)}")
        appendln("Public posts: ${getPostsPerDay(day)}")
        appendln("Private messages: ${getConvPerDay(day)}")
    }

    fun getMessageEvents(): List<MessageEvent> {

        return messageEvents.sortedBy { messageEvent -> messageEvent.time.time }
    }
    private fun init() {

        startDate = Date()

        val endCalendar = GregorianCalendar()
        endCalendar.time = startDate
        endCalendar.add(Calendar.DAY_OF_MONTH, AppConstant.daysCount)
        endDate = endCalendar.time
    }

    private fun validateRelationships(usersMap: Collection<Person>): Boolean {

        return usersMap.any { it.relationshipIds.isNotEmpty() }
    }

    private fun getDayCount(day: Int): Int {

        return messageEvents.count {
            val calendar = GregorianCalendar()

            calendar.time = it.time

            calendar.get(Calendar.DAY_OF_WEEK) == day
        }
    }

    private fun getPostsPerDay(day: Int): Int {

        return messageEvents.filter {

            val calendar = GregorianCalendar()

            calendar.time = it.time

            calendar.get(Calendar.DAY_OF_WEEK) == day && it is MessageEvent.PublicPostEvent
        }.count()
    }

    private fun getConvPerDay(day: Int): Int {

        return messageEvents.filter {

            val calendar = GregorianCalendar()

            calendar.time = it.time

            calendar.get(Calendar.DAY_OF_WEEK) == day && it is MessageEvent.ConversationMessageEvent
        }.count()
    }

    private fun createMessageEvents() {

        val it = users.values.random()

        if (it.relationshipIds.isEmpty()) return

        val id = it.relationshipIds.keys.random()
        val guid = it.relationshipIds[id]!!

        if (nextMessageTime.time >= endDate.time) return

        val recipient = users.getValue(id)

        when ((0 .. 100).random() in 0 .. AppConstant.postPercentage) {

            true -> {

                val event = MessageEvent.PublicPostEvent(

                    message = PostDomain(

                        message = messagesGenerator.getPostMessage(),
                        senderId = it.diasporaId.orEmpty(),
                        aspect = it.aspectId!!
                    ),
                    time = nextMessageTime,
                    token = it.authToken
                )

                messageEvents.offer(event)
            }

            false -> {

                val messages = messagesGenerator.getMessagesByConversation(
                    from = it,
                    to = recipient,
                    guid = guid
                )

                if ((0 .. 1000).random() % 2 == 0) return

                val event = MessageEvent.ConversationMessageEvent(

                    message = MessageDomain(

                        guid = guid,
                        senderId = it.diasporaId.orEmpty(),
                        recipientId = id.toString(),
                        message = messages.first
                    ),
                    time = nextMessageTime,
                    token = it.authToken
                )

                messageEvents.offer(event)

                if (messages.second != null) {

                    val responseEvent = MessageEvent.ConversationMessageEvent(

                        message = MessageDomain(

                            guid = guid,
                            senderId = users[id]?.diasporaId.orEmpty(),
                            recipientId = it.id.toString(),
                            message = messages.second ?: "Default response"
                        ),
                        time = getResponseEventTime(recipient.age, nextMessageTime),
                        token = recipient.authToken
                    )

                    messageEvents.offer(responseEvent)
                }
            }
        }

        updateNextMessageTime()
    }

    private fun getResponseEventTime(age: Int, currentTime: Date): Date {

        val calendar = GregorianCalendar()
        calendar.time = currentTime

        calendar.add(Calendar.MINUTE, (1..5).random())

        nextMessageTime = calendar.time

        return calendar.time
    }

    private fun updateNextMessageTime() {

        val calendar = GregorianCalendar()
        calendar.time = nextMessageTime

        val needMessageCount = statisticEventParams.getCurrentHourMessageCount(nextMessageTime)
        val remainingSeconds = 3600 - nextMessageTime.minutes * 60 - nextMessageTime.seconds

        val currentHourMessageCount = getCurrentHourMessageCount(nextMessageTime)

        when (currentHourMessageCount < needMessageCount) {

            true -> {

                val delay = remainingSeconds / (needMessageCount - currentHourMessageCount)

                calendar.add(Calendar.SECOND, (0 .. delay).random())
            }
            false -> {

                calendar.add(Calendar.HOUR, 1)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
            }
        }

        validateSendTime(calendar)

        nextMessageTime = calendar.time
    }

    private fun validateSendTime(calendar: Calendar): Boolean {

        refreshTokensTime.forEach {

            if (calendar.time.after(it.first) && calendar.time.before(it.second)) {

                calendar.time = it.second
                return true
            }
        }

        return false
    }

    private fun getCurrentHourMessageCount(date: Date): Int {

        return messageEvents.filter {

            it.time.day == date.day && it.time.hours == date.hours
        }.count()
    }
}