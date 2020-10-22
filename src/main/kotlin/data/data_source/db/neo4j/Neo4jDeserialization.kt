package data.data_source.db.neo4j

import data.data_source.db.neo4j.model.Gender
import data.data_source.db.neo4j.model.Person
import org.neo4j.driver.types.Node
import javax.inject.Inject

class Neo4jDeserialization @Inject constructor() {

    fun getPerson(node: Node): Person = with(node) {

        Person(
            get("id").asString().toInt(),
            get("name").asString(),
            get("patronymic").asString(),
            formatGender(get("gender").asString()),
            get("born").asString(),
            get("age").asString().toInt(),
            get("socialStatus").asString().split(','),
            get("profession").asString(),
            hashMapOf()
        )
    }

    private fun formatGender(gender: String): Gender = when (gender) {

        "F" -> Gender.FEMALE
        else -> Gender.MAlE
    }
}