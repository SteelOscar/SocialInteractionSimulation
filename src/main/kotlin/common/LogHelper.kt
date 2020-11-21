package common

import java.io.File
import java.io.FileWriter
import java.util.Date

object LogHelper {

    const val separator = "-----------------------------------------------------------------------------------------------------------------------------"

    private val writer by lazy {

        File("${AppConstant.GENERATOR_PATH}/social network interaction").mkdirs()
        FileWriter(File("${AppConstant.GENERATOR_PATH}/social network interaction/logs.txt"))
    }

    fun logI(message: Any) = log(message, "Info")
    fun logE(message: Any) = log(message, "Error")
    fun logD(message: Any) = log(message, "Debug")
    fun logV(message: Any) = log(message, "Verbose")

    private fun log(message: Any, type: String) {
        val time = Date()

        writer.write("$time | log $type: $message\n")
        println("$time | log $type: $message")
    }

    fun logSeparator() {

        writer.write("$separator\n")
        println(separator)
    }
}