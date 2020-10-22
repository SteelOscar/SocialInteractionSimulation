package domain.usecase

import data.data_source.api.model.body.AspectBody
import data.data_source.db.neo4j.model.Person
import domain.NetworkRepository
import javax.inject.Inject

class CreateAspectUseCase @Inject constructor(

    private val networkRepository: NetworkRepository

) {

    fun execute(user: Person) {

        val aspect = networkRepository.createAspect(
            AspectBody("aspect ${user.id}")
        )

        user.aspectId = aspect.id
    }
}