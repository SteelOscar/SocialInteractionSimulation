package data.data_source.api.model.body

import com.google.gson.annotations.SerializedName

data class CreateContactBody(

    @SerializedName("person_guid")
    val person: String
)