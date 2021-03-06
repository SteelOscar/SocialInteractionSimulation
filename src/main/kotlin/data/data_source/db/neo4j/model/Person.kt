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
    val actions: List<Action>,
    val relationshipIds: HashMap<Int,String>,
    val excludeRecipientIds: MutableSet<Int> = mutableSetOf(),
    var authToken: String = "",
    var refreshToken: String = "",
    var diasporaId: String? = "",
    var aspectId: Int? = 0
)