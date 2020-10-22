package domain.usecase

import common.AppConstant
import data.data_source.db.neo4j.model.Person
import domain.NetworkRepository
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(

    private val networkRepository: NetworkRepository,
    private val oAuthUseCase: OAuthRegisterUserUseCase

) {

    fun exec(user: Person) {

        networkRepository.createUser(user)
        oAuthUseCase.exec(user)

        AppConstant.CURRENT_USER_TOKEN = user.authToken
        val profile = networkRepository.getAuthUser()
        user.diasporaId = profile.guid
    }
}