package domain.usecase

import data.data_source.api.model.register.OAuthResponse
import domain.OAuthRegistrationRepository
import javax.inject.Inject

class OAuthUseCase @Inject constructor(

    private val oAuthRegistrationRepository: OAuthRegistrationRepository

) {

    fun exec(): OAuthResponse {

        return oAuthRegistrationRepository.register()
    }
}