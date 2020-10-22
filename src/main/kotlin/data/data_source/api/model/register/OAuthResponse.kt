package data.data_source.api.model.register

import com.google.gson.annotations.SerializedName

data class OAuthResponse(

    @SerializedName("client_id")
    val clientId: String?,

    @SerializedName("client_secret")
    val clientSecret: String?

)