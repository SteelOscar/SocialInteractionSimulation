package data.data_source.api.model.body

import com.google.gson.annotations.SerializedName

data class AccessTokenBody(

    @SerializedName("grant_type")
    val grantType: String = "authorization_code",

    @SerializedName("code")
    val code: String,
    
    @SerializedName("redirect_uri")
    val redirectUri: String,    
    
    @SerializedName("client_id")
    val clientId: String,

    @SerializedName("client_secret")
    val clientSecret: String
)