package data.data_source.api.model.response

import com.google.gson.annotations.SerializedName

data class AccessTokenResponse(

    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("refresh_token")
    val refreshToken: String,

    @SerializedName("token_type")
    val tokenType: String,

    @SerializedName("expires_in")
    val expiresIn: Int,

    @SerializedName("id_token")
    val idToken: String
)