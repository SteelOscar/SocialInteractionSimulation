package main

import common.AppConstant
import java.io.File
import java.io.FileReader

object AppConfigParser {

    fun parseConfig(path: String) {

        val file = File(path)

        val reader = FileReader(file)

        reader.forEachLine {

            if (it.startsWith("#") or it.isBlank()) return@forEachLine

            when {

                it.contains("NEO4J_IP") -> AppConstant.NEO4J_HOST = it.getValueAfterDelimiter()
                it.contains("NEO4J_USERNAME") -> AppConstant.USERNAME = it.getValueAfterDelimiter()
                it.contains("NEO4J_PASSWORD") -> AppConstant.PASSWORD = it.getValueAfterDelimiter()
                it.contains("DIASPORA_IP") -> AppConstant.DIASPORA_HOST = it.getValueAfterDelimiter()
                it.contains("LOCAL_IP") -> AppConstant.LOCAL_IP = it.getValueAfterDelimiter()
                it.contains("DAYS_COUNT") -> AppConstant.daysCount = it.getValueAfterDelimiter().toInt()
                it.contains("MESSAGES_PER_DAY") -> AppConstant.messageCountPerDay = it.getValueAfterDelimiter().toInt()
                it.contains("POST_PERCENTAGE") -> AppConstant.postPercentage = it.getValueAfterDelimiter().removeSuffix("%").toInt()
                it.contains("MONDAY") -> AppConstant.MONDAY = it.getValueAfterDelimiter().toDouble() / 100
                it.contains("TUESDAY") -> AppConstant.TUESDAY = it.getValueAfterDelimiter().toDouble() / 100
                it.contains("WEDNESDAY") -> AppConstant.WEDNESDAY = it.getValueAfterDelimiter().toDouble() / 100
                it.contains("THURSDAY") -> AppConstant.THURSDAY = it.getValueAfterDelimiter().toDouble() / 100
                it.contains("FRIDAY") -> AppConstant.FRIDAY = it.getValueAfterDelimiter().toDouble() / 100
                it.contains("SATURDAY") -> AppConstant.SATURDAY = it.getValueAfterDelimiter().toDouble() / 100
                it.contains("SUNDAY") -> AppConstant.SUNDAY = it.getValueAfterDelimiter().toDouble() / 100
                else -> error("Can't parse line - $it")
            }
        }

        reader.close()
    }

    private fun String.getValueAfterDelimiter() = this.substringAfter(": ")
}