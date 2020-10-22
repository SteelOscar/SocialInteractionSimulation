package domain.usecase

import data.data_source.db.neo4j.model.Person
import domain.OAuthRegistrationRepository
import javax.inject.Inject

class RefreshAccessTokenUseCase @Inject constructor(

    private val oAuthRegistrationRepository: OAuthRegistrationRepository,
    private val oAuthRegisterUserUseCase: OAuthRegisterUserUseCase

) {

    fun exec(user: Person) {

        runCatching {

            val response = oAuthRegistrationRepository.refreshAccessToken(user.refreshToken)

            user.authToken = response?.accessToken.orEmpty()
        }.onFailure { oAuthRegisterUserUseCase.exec(user) }
    }
}