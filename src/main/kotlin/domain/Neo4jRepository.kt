package domain

import data.data_source.db.model.Person

interface Neo4jRepository {

    fun createUsers(): HashMap<Int, Person>

    fun closeNeo4j()
}