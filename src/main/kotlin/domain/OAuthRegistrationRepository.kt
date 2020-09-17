package domain

import data.data_source.api.model.register.OAuthResponse

interface OAuthRegistrationRepository {

    fun registerApplication(): OAuthResponse

    fun getAuthenticateCode(): String

    fun getAccessToken(code: String): String
}