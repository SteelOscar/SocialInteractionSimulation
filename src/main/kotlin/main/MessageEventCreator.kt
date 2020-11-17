package main

import common.LogHelper
import data.data_source.db.neo4j.model.Person
import domain.model.MessageDomain
import domain.model.PostDomain
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

    private var currentMessageCount = 0
    private var currentHourMessageCount = 0

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

        val firstMessageCalendar = GregorianCalendar()
        firstMessageCalendar.time = startDate
        firstMessageCalendar.add(Calendar.MINUTE, 5)
        nextMessageTime = firstMessageCalendar.time

        statisticEventParams = StatisticEventParams(users.count())

        while (currentMessageCount < statisticEventParams.totalMessageEventCount) {

            createMessageEvents()
        }

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
    }

    fun getMessageEvents(): List<MessageEvent> {

        return messageEvents.sortedBy { messageEvent -> messageEvent.time.time }
    }
    private fun init() {

        startDate = Date()

        val endCalendar = GregorianCalendar()
        endCalendar.time = startDate
        endCalendar.add(Calendar.DAY_OF_MONTH, 7)
        endDate = endCalendar.time
    }

    private fun validateRelationships(usersMap: Collection<Person>): Boolean {

        return usersMap.any { it.relationshipIds.isNotEmpty() }
    }

    private fun getDayCount(day: Int): Int {

        return messageEvents.filter {

            val calendar = GregorianCalendar()

            calendar.time = it.time

            calendar.get(Calendar.DAY_OF_WEEK) == day
        }.count()
    }

    private fun createMessageEvents() {

        users.values.forEach {

            it.relationshipIds.forEach { (id, guid) ->

                val recipient = users.getValue(id)

                val messages = messagesGenerator.getMessagesByConversation(
                    from = it,
                    to = recipient,
                    guid = guid
                )

                when ((0..1000).random() % 2 != 0) {

                    true -> {

                        val event = MessageEvent.PublicPostEvent(

                            message = PostDomain(

                                message = messages.first,
                                senderId = it.diasporaId.orEmpty(),
                                aspect = it.aspectId!!
                            ),
                            time = nextMessageTime,
                            token = it.authToken
                        )

                        messageEvents.offer(event)
                    }

                    false -> {

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
        }
    }

    private fun getResponseEventTime(age: Int, currentTime: Date): Date {

        val calendar = GregorianCalendar()
        calendar.time = currentTime

        calendar.add(Calendar.MINUTE, (1..5).random())

        nextMessageTime = calendar.time

        currentMessageCount++

        return calendar.time
    }

    private fun updateNextMessageTime() {

        val calendar = GregorianCalendar()
        calendar.time = nextMessageTime

        val needMessageCount = statisticEventParams.getCurrentHourMessageCount(nextMessageTime)
        val remainingSeconds = 3600 - nextMessageTime.minutes * 60 - nextMessageTime.seconds

        when (getCurrentMessageCount(nextMessageTime) < needMessageCount) {

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

        currentMessageCount++
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

    private fun getCurrentMessageCount(date: Date): Int {

        return messageEvents.filter {

            it.time.day == date.day && it.time.hours == date.hours
        }.count()
    }
}