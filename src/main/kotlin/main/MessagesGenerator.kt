package main

import common.LogHelper
import data.data_source.db.neo4j.model.Person
import javax.inject.Inject

class MessagesGenerator @Inject constructor() {

    val personConversationPairs = HashMap<String, MessagesDialogPersonConversation>()

    fun getMessagesByConversation(from: Person, to: Person, guid: String): Pair<String, String?> {

        val dialog = getDialog(from.profRole, to.profRole)

        val conversationPairs = personConversationPairs.getOrPut(guid, { MessagesDialogPersonConversation() })

        val pairs = conversationPairs.getPairsByDialog(dialog)

        if (pairs.count() == 0) conversationPairs.updateDialogs()

        LogHelper.logD("pairs: ${pairs.count()}")

        return conversationPairs.getPairsByDialog(dialog).poll()
    }

    private fun MessagesDialogPersonConversation.getPairsByDialog(dialog: String) = when(dialog) {

        "CommonDialog" -> commonDialogPairs
        "EngineerDialog" -> engineerDialogPairs
        "ITDialog" -> itDialogPairs
        "LawyerDialog" -> lawyerDialogPairs
        "MedicineDialog" -> medicineDialogPairs
        "ScientistDialog" -> scientistDialogPairs
        "TeacherDialog" -> teacherDialogPairs
        "AgriculturalDialog" -> agriculturalDialogPairs
        "BusinessDialog" -> businessDialogPairs
        else -> error("non-existing dialog")
    }

    private fun getDialog(fromRole: String, to: String) = when (fromRole) {

        "Рыболов",
        "Работник колхоза",
        "Фермер" -> when(to) {

            "Фермер",
            "Работник колхоза",
            "Рыболов" -> "AgriculturalDialog"
            else -> "CommonDialog"
        }
        "Работник сферы индивидуальных услуг" -> when(to) {

            "Работник сферы индивидуальных услуг",
            "Бизнесмен",
            "Бухгалтер" -> "BusinessDialog"
            "Юрист" -> "LawyerDialog"
            else -> "CommonDialog"
        }
        "Юрист" -> when(to) {

            "Юрист",
            "Работник сферы индивидуальных услуг",
            "Бизнесмен",
            "Бухгалтер" -> "LawyerDialog"
            else -> "CommonDialog"
        }
        "Ученый" -> when(to) {

            "Ученый",
            "Профессор" -> "ScientistDialog"
            "Школьник",
            "Студент" -> "TeacherDialog"
            else -> "CommonDialog"
        }
        "IT-специалист",
        "Системный администратор" -> when(to) {

            "Инженер" -> "EngineerDialog"
            "Системный администратор",
            "IT-специалист" -> "ITDialog"
            else -> "CommonDialog"
        }
        "Инженер" -> when(to) {

            "Инженер",
            "Системный администратор",
            "Механик",
            "Строитель" -> "EngineerDialog"
            "IT-специалист" -> "ITDialog"
            else -> "CommonDialog"
        }
        "Бизнесмен" -> when(to) {

            "Бизнесмен",
            "Бухгалтер",
            "Работник сферы индивидуальных услуг" -> "BusinessDialog"
            "Юрист" -> "LawyerDialog"
            else -> "CommonDialog"
        }
        "Учитель",
        "Школьник" -> when(to) {

            "Школьник",
            "Учитель",
            "Студент" -> "TeacherDialog"
            else -> "CommonDialog"
        }
        "Механик" -> when(to) {

            "Инженер",
            "Системный администратор",
            "Механик",
            "Строитель" -> "EngineerDialog"
            else -> "CommonDialog"
        }
        "Студент" -> when(to) {

            "Учитель",
            "Студент",
            "Школьник",
            "Профессор" -> "TeacherDialog"
            else -> "CommonDialog"
        }
        "Мед. работник" -> when(to) {

            "Мед. работник",
            "Врач" -> "MedicineDialog"
            else -> "CommonDialog"
        }
        "Врач" -> when(to) {

            "Мед. работник",
            "Врач" -> "MedicineDialog"
            else -> "CommonDialog"
        }
        "Бухгалтер" -> when(to) {

            "Бизнесмен",
            "Бухгалтер",
            "Работник сферы индивидуальных услуг" -> "BusinessDialog"
            "Юрист" -> "LawyerDialog"
            else -> "CommonDialog"
        }
        "Строитель" -> when(to) {

            "Инженер",
            "Системный администратор",
            "Механик",
            "Строитель" -> "EngineerDialog"
            else -> "CommonDialog"
        }
        "Профессор" -> when(to) {

            "Ученый",
            "Профессор" -> "ScientistDialog"
            else -> "CommonDialog"
        }
        "Охранник",
        "Повар",
        "Продавец",
        "Менеджер",
        "Работние сельского и лесного хозяйства",
        "Специалисты высшего уровня квалификации",
        "Рабочие промышленности",
        "Пенсионер" -> "CommonDialog"
        else -> error("non-existing prof role $fromRole")
    }
}