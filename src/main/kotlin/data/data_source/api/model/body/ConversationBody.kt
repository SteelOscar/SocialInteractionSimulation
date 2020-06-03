package data.data_source.api.model.body

import com.google.gson.annotations.SerializedName

data class ConversationBody(

    @SerializedName("subject")
    val subject: String,

    @SerializedName("recipients")
    val recipients: List<String>,

    @SerializedName("body")
    val message: String

)