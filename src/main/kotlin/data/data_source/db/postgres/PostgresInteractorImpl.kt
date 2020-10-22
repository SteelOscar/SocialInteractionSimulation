package data.data_source.db.postgres

import common.AppConstant
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import javax.inject.Inject

class PostgresInteractorImpl @Inject constructor() : PostgresInteractor {

    private val db = Database.connect(
        url = "jdbc:postgresql://${AppConstant.POSTGRES_HOST}:${AppConstant.POSTGRES_PORT}/$dataBase",
        driver = "org.postgresql.Driver",
        user = username,
        password = password
    )

    override fun clearUsers() {

        transaction(db) {

//            val statement = TransactionManager.current().connection.createStatement()
//
//            statement.execute("TRUNCATE TABLE users CASCADE;")

            exec("TRUNCATE TABLE users CASCADE;")
            exec("TRUNCATE TABLE people CASCADE;")
            exec("TRUNCATE TABLE aspects CASCADE;")
            exec("TRUNCATE TABLE conversations CASCADE;")
        }
    }
}