package data.data_source.db.postgres

import common.AppConstant

interface PostgresInteractor {

    val username: String
        get() = AppConstant.POSTGRES_USERNAME

    val password: String
        get() = AppConstant.POSTGRES_PASSWORD

    val dataBase: String
        get() = "diaspora_development"

    fun clearUsers()

    fun updateTokensExpires()
}