package data.data_source.db.neo4j

import common.convertToModel
import data.data_source.db.neo4j.model.Action
import data.data_source.db.neo4j.model.ActionParse
import data.data_source.db.neo4j.model.Gender
import data.data_source.db.neo4j.model.Person
import org.neo4j.driver.types.Node
import java.text.SimpleDateFormat
import javax.inject.Inject

private const val TIME_PARSE_PATTERN = "dd/MM/yyyy HH:mm"

class Neo4jDeserialization @Inject constructor() {

    private val simpleDateFormat = SimpleDateFormat(TIME_PARSE_PATTERN)

    fun getPerson(node: Node): Person = with(node) {

        Person(
            get("id").asString().toInt(),
            get("name").asString(),
            get("surname").asString(),
            formatGender(get("gender").asString()),
            get("born").asString(),
            get("age").asString().toInt(),
            get("socialStatus").asString().split(','),
            get("profession").asString(),
            get("action").asList {

                val parse = it.asString().convertToModel<ActionParse>()
                Action(
                    public = parse.type != "private",
                    recipientId = parse.target,
                    time = simpleDateFormat.parse(parse.time),
                    message = parse.text
                )
            },
            hashMapOf()
        )
    }

    private fun formatGender(gender: String): Gender = when (gender) {

        "F" -> Gender.FEMALE
        else -> Gender.MAlE
    }
}