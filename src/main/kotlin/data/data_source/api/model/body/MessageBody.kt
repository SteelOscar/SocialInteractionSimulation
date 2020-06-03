package data.data_source.api.model.body

import com.google.gson.annotations.SerializedName

data class MessageBody(

    @SerializedName("body")
    val message: String
)