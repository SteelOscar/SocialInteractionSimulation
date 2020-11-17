package main

import java.io.File
import java.io.FileReader
import java.util.LinkedList
import java.util.Queue

class MessagesDialogPersonConversation {

    lateinit var commonDialogPairs: Queue<Pair<String,String?>>
    lateinit var engineerDialogPairs: Queue<Pair<String,String?>>
    lateinit var itDialogPairs: Queue<Pair<String,String?>>
    lateinit var lawyerDialogPairs: Queue<Pair<String,String?>>
    lateinit var medicineDialogPairs: Queue<Pair<String,String?>>
    lateinit var scientistDialogPairs: Queue<Pair<String,String?>>
    lateinit var teacherDialogPairs: Queue<Pair<String,String?>>
    lateinit var agriculturalDialogPairs: Queue<Pair<String,String?>>
    lateinit var businessDialogPairs: Queue<Pair<String,String?>>

    init {
        updateDialogs()
    }

    fun updateDialogs() {

        val builder = ProcessBuilder()

        builder.command("bash", "-c", "python3 /home/renat/IdeaProjects/SocialInteractionSimulation/DialogGenerate.py")

        val process = builder.start()

        process.waitFor()
        process.destroy()

        commonDialogPairs = getMessagePairsFromDialog("CommonDialog.txt")
        engineerDialogPairs = getMessagePairsFromDialog("EngineerDialog.txt")
        itDialogPairs = getMessagePairsFromDialog("ITDialog.txt")
        lawyerDialogPairs = getMessagePairsFromDialog("LawyerDialog.txt")
        medicineDialogPairs = getMessagePairsFromDialog("MedicineDialog.txt")
        scientistDialogPairs = getMessagePairsFromDialog("ScientistDialog.txt")
        teacherDialogPairs = getMessagePairsFromDialog("TeacherDialog.txt")
        agriculturalDialogPairs = getMessagePairsFromDialog("AgriculturalDialog.txt")
        businessDialogPairs = getMessagePairsFromDialog("BusinessDialog.txt")
    }

    private fun getMessagePairsFromDialog(dialogFileName: String): Queue<Pair<String,String?>> {

        val targetFile = File("/home/renat/IdeaProjects/SocialInteractionSimulation/dialogs/$dialogFileName")
        val fileReader = FileReader(targetFile)
        val lines = fileReader.readLines()
        fileReader.close()

        return LinkedList<Pair<String,String?>>().apply {

            lines.forEachIndexed { index, s ->

                if (index == lines.count() - 2 || index % 2 != 0) return@forEachIndexed

                add(s.substringAfter("]:") to lines.getOrNull(index + 2)?.substringAfter("]:"))
            }
        }
    }
}