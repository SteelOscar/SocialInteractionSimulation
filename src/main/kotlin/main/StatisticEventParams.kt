package main

import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

class StatisticEventParams(

    usersCount: Int

) {
    /**
     * 20 - message count for person per day
     * 7 - day count (week)
     */
    val totalMessageEventCount = usersCount * 6 * 7

    private val mondayMessageEventCount = (totalMessageEventCount * 0.144).toInt()
    private val tuesdayMessageEventCount = (totalMessageEventCount * 0.137).toInt()
    private val wednesdayMessageEventCount = (totalMessageEventCount * 0.139).toInt()
    private val thursdayMessageEventCount = (totalMessageEventCount * 0.146).toInt()
    private val fridayMessageEventCount = (totalMessageEventCount * 0.137).toInt()
    private val saturdayMessageEventCount = (totalMessageEventCount * 0.147).toInt()
    private val sundayMessageEventCount =(totalMessageEventCount * 0.15).toInt()

    fun getCurrentHourMessageCount(currentTime: Date): Int {

        val hourPercent = when(currentTime.hours) {

            0 -> (40 .. 46).random() / 1000f
            1 -> (37 .. 43).random() / 1000f
            2 -> (32 .. 38).random() / 1000f
            3 -> (32 .. 38).random() / 1000f
            4 -> (35 .. 41).random() / 1000f
            5 -> (32 .. 38).random() / 1000f
            6 -> (38 .. 44).random() / 1000f
            7 -> (36 .. 42).random() / 1000f
            8 -> (39 .. 45).random() / 1000f
            9 -> (42 .. 48).random() / 1000f
            10 -> (41 .. 47).random() / 1000f
            11 -> (43 .. 49).random() / 1000f
            12 -> (42 .. 48).random() / 1000f
            13 -> (37 .. 43).random() / 1000f
            14 -> (43 .. 49).random() / 1000f
            15 -> (37 .. 43).random() / 1000f
            16 -> (40 .. 46).random() / 1000f
            17 -> (43 .. 49).random() / 1000f
            18 -> (37 .. 43).random() / 1000f
            19 -> (38 .. 44).random() / 1000f
            20 -> (40 .. 46).random() / 1000f
            21 -> (41 .. 47).random() / 1000f
            22 -> (40 .. 46).random() / 1000f
            else -> (41 .. 47).random() / 1000f
        }

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