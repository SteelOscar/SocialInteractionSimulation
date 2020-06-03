package data.data_source.api.model.response

import com.google.gson.annotations.SerializedName

data class Conversation(

    @SerializedName("guid")
    val guid: String,

    @SerializedName("subject")
    val subject: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("participants")
    val participants: List<Participant>
)