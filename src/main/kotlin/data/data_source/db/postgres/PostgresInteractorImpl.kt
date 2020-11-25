package data.data_source.db.postgres

import common.AppConstant
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import javax.inject.Inject

class PostgresInteractorImpl @Inject constructor() : PostgresInteractor {

    private val db = Database.connect(
        url = "jdbc:postgresql://${AppConstant.DIASPORA_HOST}:${AppConstant.POSTGRES_PORT}/$dataBase",
        driver = "org.postgresql.Driver",
        user = username,
        password = password
    )

    override fun clearUsers() {

        transaction(db) {

            exec("TRUNCATE TABLE users CASCADE;")
            exec("TRUNCATE TABLE people CASCADE;")
            exec("TRUNCATE TABLE aspects CASCADE;")
            exec("TRUNCATE TABLE conversations CASCADE;")
            exec("TRUNCATE TABLE o_auth_access_tokens CASCADE;")
            exec("TRUNCATE TABLE contacts CASCADE;")
            exec("TRUNCATE TABLE authorizations CASCADE;")
            exec("TRUNCATE TABLE profiles CASCADE;")
            exec("TRUNCATE TABLE aspect_memberships CASCADE;")
        }
    }

    override fun updateTokensExpires() {

        val calendar = GregorianCalendar()
        calendar.time = Date()
        calendar.add(Calendar.DAY_OF_MONTH, 8)

        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS")

        transaction(db) {

            exec("UPDATE o_auth_access_tokens SET expires_at='${formatter.format(calendar.time)}'")
        }
    }
}