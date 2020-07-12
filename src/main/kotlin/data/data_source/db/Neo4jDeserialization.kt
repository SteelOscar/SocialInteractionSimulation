package data.data_source.db

import data.data_source.db.model.Gender
import data.data_source.db.model.Person
import org.neo4j.driver.types.Node
import javax.inject.Inject

class Neo4jDeserialization @Inject constructor() {

    fun getPerson(node: Node): Person = with(node) {

        Person(
            get("id").asString().toInt(),
            get("name").asString(),
            get("surname").asString(),
            formatGender(get("gender").asString()),
            get("born").asString(),
            get("age").asString().toInt(),
            get("social_role").asString().split(','),
            get("prof_role").asString(),
            hashSetOf()
        )
    }

    private fun formatGender(gender: String): Gender = when (gender) {

        "F" -> Gender.FEMALE
        else -> Gender.MAlE
    }
}