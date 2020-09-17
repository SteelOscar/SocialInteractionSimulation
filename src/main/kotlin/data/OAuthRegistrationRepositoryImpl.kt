package data

import com.github.scribejava.core.builder.ServiceBuilder
import common.AppConstant
import common.convertToModel
import common.postModel
import data.data_source.api.ApiInteractor
import data.data_source.api.DiasporaApi
import data.data_source.api.model.register.OAuthBody
import data.data_source.api.model.register.OAuthResponse
import data.data_source.api.model.response.AccessTokenResponse
import data.data_source.socket.SocketInteractor
import domain.OAuthRegistrationRepository
import org.mozilla.javascript.Context.enter
import java.awt.Desktop
import java.net.URI
import javax.inject.Inject
import javax.naming.Context
import javax.script.ScriptEngineManager

class OAuthRegistrationRepositoryImpl @Inject constructor(

    private val apiInteractor: ApiInteractor,
    private val socketInteractor: SocketInteractor

) : OAuthRegistrationRepository {

    override fun registerApplication(): OAuthResponse {

        return apiInteractor.postModel(
            url = AppConstant.BASE_URL_OAUTH + "/clients",
            body = OAuthBody(
                applicationType = "native",
                clientName = "SocialInteraction",
                redirectUris = listOf(AppConstant.REDIRECT_URI)
            )
        )
    }

    override fun getAuthenticateCode(): String {

//        Desktop.getDesktop().browse(URI(AppConstant.AUTHENTICATION_CODE_URL))

        Runtime.getRuntime().exec("firefox ./test.html")

        return socketInteractor.interceptResponseAuthCode()
    }

    override fun getAccessToken(code: String): String {

        val service = ServiceBuilder(AppConstant.CLIENT_ID)
            .apiSecret(AppConstant.CLIENT_SECRET)
            .callback(AppConstant.REDIRECT_URI)
            .build(DiasporaApi.instance)

        runCatching {

            service.getAccessToken(code)
        }.onFailure {

            val tokenResponse = it.message
                ?.substringAfter(": '")
                ?.removeSuffix("'")
                .orEmpty()

            println(tokenResponse)

            val response = tokenResponse.convertToModel<AccessTokenResponse>()

            return response.accessToken
        }

        return "token"
    }
}