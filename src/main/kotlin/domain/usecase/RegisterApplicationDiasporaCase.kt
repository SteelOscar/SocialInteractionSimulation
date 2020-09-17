package domain.usecase

import domain.OAuthRegistrationRepository
import javax.inject.Inject

class RegisterApplicationDiasporaCase @Inject constructor(

    private val oAuthRegistrationRepository: OAuthRegistrationRepository

) {

    fun execute() = oAuthRegistrationRepository.registerApplication()
}