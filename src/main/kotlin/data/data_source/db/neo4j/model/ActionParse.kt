package data.data_source.db.neo4j.model

import com.google.gson.annotations.SerializedName

data class ActionParse(

    @SerializedName("type")
    val type: String,

    @SerializedName("target")
    val target: String,

    @SerializedName("time")
    val time: String,

    @SerializedName("text")
    val text: String
)