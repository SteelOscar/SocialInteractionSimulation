package domain.usecase

import domain.NetworkRepository
import domain.model.MessageDomain
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(

    private val repository: NetworkRepository

) {

    fun execute(domain: MessageDomain) = repository.sendMessage(domain)
}