package domain.usecase

import common.LogHelper
import common.convertCyrillic
import data.data_source.db.neo4j.model.Person
import domain.OAuthRegistrationRepository
import javax.inject.Inject

class OAuthRegisterUserUseCase @Inject constructor(

    private val oAuthRegistrationRepository: OAuthRegistrationRepository

) {

    fun exec(user: Person) {

        val authCode = oAuthRegistrationRepository.getAuthenticateCode(
            login = (user.name + user.age).convertCyrillic(),
            password = "qazwsxed9"
        )

        if (authCode == null) exec(user)

        val accessTokenResponse = oAuthRegistrationRepository.getAccessToken(authCode!!)

        user.authToken = accessTokenResponse?.accessToken.orEmpty()
        user.refreshToken = accessTokenResponse?.refreshToken.orEmpty()

        LogHelper.logD("User ${user.name} ${user.surname} successfully registered!")
    }
}