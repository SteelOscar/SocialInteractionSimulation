package data.data_source.db.postgres

import data.data_source.db.postgres.model.UserProfile

interface PostgresInteractor {

    val username: String
        get() = "diaspora"

    val password: String
        get() = "12345"

    val dataBase: String
        get() = "diaspora_development"

    fun insertUser(user: UserProfile)

    fun getUser(id: Int): UserProfile

    fun getLastUser(): UserProfile
}