package domain.usecase

import domain.NetworkRepository
import domain.model.ConversationDomain
import javax.inject.Inject

class CreateConversationUseCase @Inject constructor(

    private val repository: NetworkRepository

) {

    fun execute(model: ConversationDomain) = repository.createConversation(model)
}