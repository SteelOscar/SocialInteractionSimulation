package data.data_source.api.model.response

import com.google.gson.annotations.SerializedName

data class AspectResponse(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("order")
    val order: Int
)