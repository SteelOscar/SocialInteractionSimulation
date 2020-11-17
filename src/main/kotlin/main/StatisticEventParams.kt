package main

import common.AppConstant.FRIDAY
import common.AppConstant.MONDAY
import common.AppConstant.SATURDAY
import common.AppConstant.SUNDAY
import common.AppConstant.THURSDAY
import common.AppConstant.TUESDAY
import common.AppConstant.WEDNESDAY
import common.AppConstant.daysCount
import common.AppConstant.messageCountPerDay
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

class StatisticEventParams(

    usersCount: Int

) {

    val totalMessageEventCount = usersCount * messageCountPerDay * daysCount

    private val mondayMessageEventCount = (totalMessageEventCount * MONDAY).toInt()
    private val tuesdayMessageEventCount = (totalMessageEventCount * TUESDAY).toInt()
    private val wednesdayMessageEventCount = (totalMessageEventCount * WEDNESDAY).toInt()
    private val thursdayMessageEventCount = (totalMessageEventCount * THURSDAY).toInt()
    private val fridayMessageEventCount = (totalMessageEventCount * FRIDAY).toInt()
    private val saturdayMessageEventCount = (totalMessageEventCount * SATURDAY).toInt()
    private val sundayMessageEventCount =(totalMessageEventCount * SUNDAY).toInt()

    private val hourPercents = mutableListOf(

        (43 .. 46).random(),
        (37 .. 43).random(),
        (32 .. 38).random(),
        (32 .. 38).random(),
        (35 .. 41).random(),
        (32 .. 38).random(),
        (38 .. 44).random(),
        (36 .. 42).random(),
        (39 .. 45).random(),
        (42 .. 48).random(),
        (41 .. 47).random(),
        (43 .. 49).random(),
        (42 .. 48).random(),
        (37 .. 43).random(),
        (43 .. 49).random(),
        (37 .. 43).random(),
        (40 .. 46).random(),
        (43 .. 49).random(),
        (37 .. 43).random(),
        (38 .. 44).random(),
        (40 .. 46).random()
    )

    init {

        hourPercents.add(((1000 - hourPercents.sum()) / 3) - (-2 .. 2).random())
        hourPercents.add(((1000 - hourPercents.sum()) / 2) - (-2 .. 2).random())
        hourPercents.add(1000 - hourPercents.sum())
    }

    fun getCurrentHourMessageCount(currentTime: Date): Int {

        val hourPercent = hourPercents[currentTime.hours] / 1000f

        val calendar = GregorianCalendar()
        calendar.time = currentTime

        return when(calendar.get(Calendar.DAY_OF_WEEK)) {

            Calendar.MONDAY -> mondayMessageEventCount * hourPercent
            Calendar.TUESDAY -> tuesdayMessageEventCount * hourPercent
            Calendar.WEDNESDAY -> wednesdayMessageEventCount * hourPercent
            Calendar.THURSDAY -> thursdayMessageEventCount * hourPercent
            Calendar.FRIDAY -> fridayMessageEventCount * hourPercent
            Calendar.SATURDAY -> saturdayMessageEventCount * hourPercent
            else -> sundayMessageEventCount * hourPercent
        }.toInt()
    }
}