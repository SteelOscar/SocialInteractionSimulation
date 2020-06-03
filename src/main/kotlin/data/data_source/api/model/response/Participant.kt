package data.data_source.api.model.response

import com.google.gson.annotations.SerializedName

data class Participant(

    @SerializedName("guid")
    val guid: String,

    @SerializedName("diaspora_id")
    val diasporaId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("avatar")
    val avatar: String
)