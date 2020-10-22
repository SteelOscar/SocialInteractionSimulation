package domain.usecase

import data.data_source.api.model.body.CreateContactBody
import domain.NetworkRepository
import javax.inject.Inject

class CreateContactUseCase @Inject constructor(

    private val networkRepository: NetworkRepository

) {

    fun execute(aspectId: Int, person: String) {

        networkRepository.createContact(
            aspectId = aspectId,
            body = CreateContactBody(person)
        )
    }
}