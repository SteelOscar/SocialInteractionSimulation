package data.data_source.api.model.response

import com.google.gson.annotations.SerializedName

data class Message(

    @SerializedName("guid")
    val guid: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("body")
    val message: String,

    @SerializedName("author")
    val author: Participant
)