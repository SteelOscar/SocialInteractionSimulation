package data.data_source.db.neo4j.model

import java.util.Date

data class Action(

    val public: Boolean,
    val recipientId: String,
    val time: Date,
    val message: String
)