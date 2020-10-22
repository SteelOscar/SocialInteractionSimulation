package main

import common.LogHelper
import data.data_source.db.neo4j.model.Person
import domain.model.MessageDomain
import java.io.File
import java.io.FileWriter
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.PriorityQueue
import javax.inject.Inject

class MessageEventMockCreator @Inject constructor() {

    private val initDate = Date()

    private val refreshTokensTime = arrayListOf<Pair<Date, Date>>()

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

        init()

        val firstMessageCalendar = GregorianCalendar()
        firstMessageCalendar.time = startDate
        firstMessageCalendar.add(Calendar.MINUTE, 1)
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

        val updatePeriodMinutes = (users.count() * 16 ) / 60 + 1

        var lastPeriod = 0

        (1 .. 20).forEach {

            lastPeriod += 10

            val initCalendar = GregorianCalendar()
            initCalendar.time = initDate
            initCalendar.add(Calendar.MINUTE, lastPeriod)

            val from = initCalendar.time

            val (event, delay) = when (it % 2 != 0) {

                true -> MessageEvent.RefreshTokens(from) to 1
                false -> MessageEvent.NewAccessTokens(from) to updatePeriodMinutes
            }

            lastPeriod += delay

            initCalendar.add(Calendar.MINUTE, delay)
            val to = initCalendar.time

            refreshTokensTime.add(from to to)

            messageEvents.offer(event)
        }

        val fileRefreshEvents = File("/home/renat/Desktop/social network interaction/refreshEvents.txt")
        val writer = FileWriter(fileRefreshEvents, false)

        refreshTokensTime.forEach { pair ->

            val event = messageEvents.find { it.time.time == pair.first.time }

            val type = when(event) {

                is MessageEvent.RefreshTokens -> "refresh tokens"
                is MessageEvent.NewAccessTokens -> "generating tokens"
                else -> "null"
            }

            writer.write("type: $type, from: ${pair.first.toLocaleString()}, to: ${pair.second.toLocaleString()}\n")
        }
        writer.close()
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

                val event = MessageEvent.ConversationMessageEvent(

                    message = MessageDomain(

                        guid = guid,
                        senderId = it.id.toString(),
                        recipientId = id.toString(),
                        message = "Message from ${it.name} to ${recipient.name}"
                    ),
                    time = nextMessageTime,
                    token = it.authToken
                )

                val responseEvent = MessageEvent.ConversationMessageEvent(

                    message = MessageDomain(

                        guid = guid,
                        senderId = id.toString(),
                        recipientId = it.id.toString(),
                        message = "Response from ${recipient.name} to ${it.name}"
                    ),
                    time = getResponseEventTime(),
                    token = recipient.authToken
                )

                messageEvents.offer(event)
                messageEvents.offer(responseEvent)

                updateNextMessageTime()
            }
        }
    }

    private fun updateNextMessageTime() {

        val calendar = GregorianCalendar()
        calendar.time = nextMessageTime
        calendar.add(Calendar.SECOND,3)

        validateSendTime(calendar)

        currentMessageCount++

        nextMessageTime = calendar.time
    }

    private fun getResponseEventTime(): Date {

        val calendar = GregorianCalendar()
        calendar.time = nextMessageTime
        calendar.add(Calendar.SECOND,3)

        if (validateSendTime(calendar)) {

            calendar.add(Calendar.SECOND, (0 .. 100).random() / 10)
        }

        currentMessageCount++

        return calendar.time
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
}