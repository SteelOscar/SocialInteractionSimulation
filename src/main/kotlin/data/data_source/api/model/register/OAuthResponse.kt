package data.data_source.api.model.register

import com.google.gson.annotations.SerializedName

data class OAuthResponse(

    @SerializedName("application_type")
    private val applicationType: String?,

    @SerializedName("client_id")
    private val clientId: String?,

    @SerializedName("client_secret")
    private val clientName: String?,

    @SerializedName("client_uri")
    private val clientSecret: String?,

    @SerializedName("client_uri")
    private val clientUri: String?,

    @SerializedName("contacts")
    private val contacts: String?,

    @SerializedName("created_at")
    private val createdAt: String?,

    @SerializedName("created_at")
    private val grantTypes: String?,

    @SerializedName("id")
    private val id : Int?,

    @SerializedName("jwks")
    private val jwks: String?,

    @SerializedName("jwks_uri")
    private val jwksUri: String?,

    @SerializedName("logo_uri")
    private val logoUri: String?,

    @SerializedName("policy_uri")
    private val policyUri: String?,

    @SerializedName("ppid")
    private val ppid: Boolean?,

    @SerializedName("redirect_uris")
    private val redirectUris: List<String>?,

    @SerializedName("response_types")
    private val responseTypes: String?,

    @SerializedName("sector_identifier_uri")
    private val sectorIdentifierUri: String?,

    @SerializedName("token_endpoint_auth_method")
    private val tokenEndpointAuthMethod: String?,

    @SerializedName("tos_uri")
    private val tosUri: String?,

    @SerializedName("updated_at")
    private val updatedAt: String?,

    @SerializedName("user_id")
    private val userId: Int?

)