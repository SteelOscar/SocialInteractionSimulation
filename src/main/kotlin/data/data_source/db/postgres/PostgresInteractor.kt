package data.data_source.db.postgres

interface PostgresInteractor {

    val username: String
        get() = "diaspora"

    val password: String
        get() = "12345"

    val dataBase: String
        get() = "diaspora_development"

    fun clearUsers()

    fun updateTokensExpires()
}