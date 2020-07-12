package domain.usecase

import data.data_source.api.model.response.UserProfile
import domain.NetworkRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(

    private val networkRepository: NetworkRepository

) {

    fun exec(): UserProfile  = networkRepository.getAuthUser()
}