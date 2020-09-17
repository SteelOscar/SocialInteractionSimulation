package domain.usecase

import data.data_source.db.neo4j.model.Person
import domain.OAuthRegistrationRepository
import javax.inject.Inject

class OAuthRegisterUserUseCase @Inject constructor(

    private val oAuthRegistrationRepository: OAuthRegistrationRepository

) {

    fun exec(user: Person?) {

        val authCode = oAuthRegistrationRepository.getAuthenticateCode()
        val accessToken = oAuthRegistrationRepository.getAccessToken(authCode)

        user?.authToken = accessToken

        println("authCode = $authCode")
        println("accessToken = $accessToken")
    }
}