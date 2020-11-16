package domain.usecase

import common.AppConstant
import domain.NetworkRepository
import domain.model.UpdateUserDomain
import javax.inject.Inject

class SetPublicProfileInfoCase @Inject constructor(

    private val networkRepository: NetworkRepository

) {

    fun execute(token: String) {

        AppConstant.CURRENT_USER_TOKEN = token

        networkRepository.updateUserProfile(
            model = UpdateUserDomain(

                publicProfileInfo = true
            )
        )
    }
}