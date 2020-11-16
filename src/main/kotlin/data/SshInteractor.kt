package data

import common.AppConstant
import common.LogHelper
import common.convertCyrillic
import data.data_source.db.neo4j.model.Gender
import data.data_source.db.neo4j.model.Person
import kossh.impl.SSH
import kossh.impl.SSHOptions
import javax.inject.Inject

class SshInteractor @Inject constructor() {

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
            val gender = when(user.gender) {

                Gender.MAlE -> ""
                Gender.FEMALE -> ""
            }
            LogHelper.logD(execute(
                "rails r $scriptPath ${(user.name + user.surname + user.age).convertCyrillic()} $password" +
                        " ${user.name} ${user.surname} $gender ${user.profRole} ${user.born}"
            ))
        }
    }
}