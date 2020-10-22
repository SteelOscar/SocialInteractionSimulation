package domain.usecase

import common.AppConstant
import domain.OAuthRegistrationRepository
import javax.inject.Inject

class RegisterApplicationDiasporaCase @Inject constructor(

    private val oAuthRegistrationRepository: OAuthRegistrationRepository

) {

    fun execute() {

        val response = oAuthRegistrationRepository.registerApplication()

        AppConstant.CLIENT_ID = response?.clientId.orEmpty()
        AppConstant.CLIENT_SECRET = response?.clientSecret.orEmpty()
    }
}