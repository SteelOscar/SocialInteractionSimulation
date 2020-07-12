package data.data_source.api.model.register

import com.google.gson.annotations.SerializedName

data class OAuthBody(

    @SerializedName("client_name")
    private val clientName: String,

    @SerializedName("redirect_uris")
    private val redirectUris: String = "http://example.com/"

)