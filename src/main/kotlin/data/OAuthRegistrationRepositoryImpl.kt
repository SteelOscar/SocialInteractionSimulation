package data

import common.AppConstant
import common.postModel
import data.data_source.api.ApiInteractor
import data.data_source.api.model.register.OAuthBody
import data.data_source.api.model.register.OAuthResponse
import domain.OAuthRegistrationRepository
import javax.inject.Inject

class OAuthRegistrationRepositoryImpl @Inject constructor(

    private val apiInteractor: ApiInteractor

) : OAuthRegistrationRepository {

    override fun register(): OAuthResponse {

        return apiInteractor.postModel(
            url = AppConstant.BASE_URL_OAUTH,
            body = OAuthBody(
                "SocialInteraction",
                "http://social-interaction.com/"
            )
        )
    }
}