package data.data_source.db.neo4j

import common.AppConstant.BASE_URL_NEO4J
import common.AppConstant.PASSWORD
import common.AppConstant.USERNAME
import org.neo4j.driver.AuthTokens
import org.neo4j.driver.GraphDatabase
import org.neo4j.driver.Session
import javax.inject.Inject

class Neo4jDriverManager @Inject constructor() {

    private val driver = GraphDatabase.driver(
        BASE_URL_NEO4J,
        AuthTokens.basic(USERNAME, PASSWORD)
    )

    fun createSession() : Session {

        return driver.session()
    }

    fun closeDriver() {

        driver.close()
    }
}