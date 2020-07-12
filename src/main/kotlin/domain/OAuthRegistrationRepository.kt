package domain

import data.data_source.api.model.register.OAuthResponse

interface OAuthRegistrationRepository {

    fun register(): OAuthResponse
}