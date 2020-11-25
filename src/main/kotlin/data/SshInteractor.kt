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

    private val scriptPath = "./createUserDiaspora.rb"

    fun createUser(user: Person, password: String) {

        val options = SSHOptions(
            host = AppConstant.DIASPORA_HOST,
            username = AppConstant.SSH_USERNAME,
            password = AppConstant.SSH_PASSWORD,
            port = AppConstant.SSH_PORT
        )

        SSH.shell(options) {

            execute("cd ./diaspora")
            val gender = when(user.gender) {

                Gender.MAlE -> "Мужской"
                Gender.FEMALE -> "Женский"
            }
            LogHelper.logD(execute(
                "rails r $scriptPath ${(user.name + user.surname + user.age).convertCyrillic()} $password" +
                        " ${user.name} ${user.surname} $gender ${user.profRole} ${user.born}"
            ))
        }
    }
}