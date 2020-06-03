package data

import data.data_source.db.Neo4jDeserialization
import data.data_source.db.Neo4jDriverManager
import data.data_source.db.model.Person
import domain.Neo4jRepository
import javax.inject.Inject

class Neo4jRepositoryImpl @Inject constructor (

    private val neo4j: Neo4jDriverManager,
    private val neo4jDeserialization: Neo4jDeserialization

) : Neo4jRepository {

    private val neo4jSession = neo4j.createSession()
    private val userMap = HashMap<Int, Person>()

    override fun createUsers(): HashMap<Int, Person> {

        val result = neo4jSession.run("MATCH (p:Person) RETURN p")

        while (result.hasNext()) {

            val person = neo4jDeserialization.getPerson(result.next().get(0).asNode())

            userMap[person.id] = person
            setRelationship(person.id)
        }

        return userMap
    }

    private fun setRelationship(userId: Int) {

        val result = neo4jSession.run("MATCH (p:Person {id: \"$userId\"})-[r:KNOW]->(e:Person) return e")

        while (result.hasNext()) {

            val person = neo4jDeserialization.getPerson(result.next().get(0).asNode())

            userMap[userId]?.relationshipIds?.add(person.id)
        }
    }

    override fun closeNeo4j() {
        neo4jSession.close()
        neo4j.closeDriver()
    }
}