package data

import common.AppConstant
import data.data_source.db.neo4j.model.Person
import kossh.impl.SSH
import kossh.impl.SSHOptions
import javax.inject.Inject

class SshInteractor @Inject constructor(

) {

    private val sshUserName = "diaspora"
    private val sshPassword = "12345"
    private val sshPort = 22

    private val scriptPath = "./createUserDiaspora.rb"

    fun createUser(user: Person, password: String) {

        val options = SSHOptions(
            host = AppConstant.POSTGRES_HOST,
            username = sshUserName,
            password = sshPassword,
            port = sshPort
        )

        SSH.shell(options) {

            execute("cd ./diaspora")
            println(execute(
                "rails r $scriptPath ${(user.name + user.age).convertCyrillic()} $password" +
                        " ${user.name} ${user.surname} ${user.gender} ${user.profRole} ${user.born}"
            ))
        }
    }

    private fun String.convertCyrillic(): String? {

        val cyr = arrayOf(
            "а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "й", "к", "л", "м", "н", "о", "п",
            "р", "с", "т", "у", "ф", "х", "ц", "ч", "ю", "я", "ш", "щ", "ь", "А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З",
            "И", "К", "Л", "М", "Н", "О", "П", "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ю", "Я", "Ш", "Щ"
        )
        val lat = arrayOf(
            "a", "b", "v", "g", "d", "e", "e", "zh", "z", "i", "y", "k", "l", "m", "n", "o", "p",
            "r", "s", "t", "u", "f", "h", "ts", "ch", "yu", "ya", "sh", "shch", "", "A", "B", "V", "G", "D", "E", "E", "Zh", "Z",
            "I", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "F", "H", "Ts", "Ch", "Yu", "Ya", "Sh", "Shch"
        )

        var formattedString = this

        cyr.forEachIndexed { index, c -> formattedString = formattedString.replace(c, lat[index]) }

        return formattedString
    }
}