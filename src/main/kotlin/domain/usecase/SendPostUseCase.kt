package domain.usecase

import domain.NetworkRepository
import domain.model.PostDomain
import javax.inject.Inject

class SendPostUseCase @Inject constructor(

    private val repository: NetworkRepository

) {

    fun execute(post: PostDomain) = repository.sendPost(post)
}