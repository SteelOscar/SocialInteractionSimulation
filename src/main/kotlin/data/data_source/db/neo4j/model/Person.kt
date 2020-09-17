package data.data_source.db.neo4j.model

data class Person(

    val id: Int,
    val name: String,
    val surname: String,
    val gender: Gender,
    val born: String,
    val age: Int,
    val socialRole: List<String>,
    val profRole: String,
    val relationshipIds: HashSet<Int>
) {

    var authToken: String? = null
}