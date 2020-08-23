package domain.usecase

import data.data_source.db.neo4j.model.Person
import domain.NetworkRepository
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(

    private val networkRepository: NetworkRepository

) {

    fun exec(user: Person) = networkRepository.createUser(user)
}