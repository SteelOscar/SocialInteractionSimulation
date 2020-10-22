package common

import java.util.Date

object LogHelper {

    private const val separator = "-----------------------------------------------------------------------------------------------------------------------------"

    fun logI(message: Any) = log(message, "Info")
    fun logE(message: Any) = log(message, "Error")
    fun logD(message: Any) = log(message, "Debug")
    fun logV(message: Any) = log(message, "Verbose")

    private fun log(message: Any, type: String) {
        val time = Date()

        println("$time | log $type: $message")
    }

    fun logSeparator() {

        println("$separator")
    }
}