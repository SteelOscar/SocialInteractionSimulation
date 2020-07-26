package domain.usecase

import domain.NetworkRepository
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(

    private val networkRepository: NetworkRepository

) {

    fun exec(username: String) = networkRepository.createUser(username)
}