package data

import com.github.scribejava.core.builder.ServiceBuilder
import common.AppConstant
import common.LogHelper
import common.convertToModel
import data.data_source.api.DiasporaApi
import data.data_source.api.SocialAPI
import data.data_source.api.model.register.OAuthBody
import data.data_source.api.model.register.OAuthResponse
import data.data_source.api.model.response.AccessTokenResponse
import di.DiProvider
import domain.OAuthRegistrationRepository
import org.openqa.selenium.By
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.firefox.FirefoxDriver
import javax.inject.Inject

class OAuthRegistrationRepositoryImpl @Inject constructor(

    private val socialApiProvider: DiProvider<SocialAPI>

) : OAuthRegistrationRepository {

    private val service by lazy {

        ServiceBuilder(AppConstant.CLIENT_ID)
            .apiSecret(AppConstant.CLIENT_SECRET)
            .callback(AppConstant.REDIRECT_URI)
            .build(DiasporaApi.instance)
    }

    override fun registerApplication(): OAuthResponse? {

        val response = socialApiProvider.provide().post(
            url = AppConstant.BASE_URL_OAUTH + "/clients",
            body = OAuthBody(
                applicationType = "native",
                clientName = "SocialInteraction",
                redirectUris = listOf(AppConstant.REDIRECT_URI)
            ),
            headers = emptyMap()
        ).execute()

        val asd = response.body()?.string()

        return asd?.convertToModel()
    }

    override fun getAuthenticateCode(login: String, password: String): String? {

        val driver = FirefoxDriver()

        driver.get(AppConstant.LOGIN_URL)

        val loginView = driver.findElementById("user_username")
        val passwordView = driver.findElementById("user_password")
        val loginButtonView = driver.findElementsByName("commit")[0]

        loginView.sendKeys(login)
        passwordView.sendKeys(password)
        loginButtonView.click()

        driver.navigate().to(AppConstant.AUTHENTICATION_CODE_URL)

        val approveButtonView = driver.findElements(By.className("approval-button"))[1]

        runCatching { approveButtonView.click() }.onFailure {

            return when (it) {

                is WebDriverException -> {

                    driver.quit()

                    LogHelper.logD("authCode: ${it.message}")
                    LogHelper.logD("------------------------------\n")

                    it.message
                        ?.substringAfter("code%3D")
                        ?.substringBefore("&c=").orEmpty()
                }
                else -> getAuthenticateCode(login, password)
            }
        }

        return null
    }

    override fun getAccessToken(code: String): AccessTokenResponse? {

        val tokenResponse = service.getAccessToken(code)

        LogHelper.logD("accessTokenSuccess: ${tokenResponse.accessToken}, code: $code")
        LogHelper.logD("------------------------------")

        return AccessTokenResponse(

            accessToken = tokenResponse.accessToken,
            refreshToken = tokenResponse.refreshToken,
            tokenType = tokenResponse.tokenType,
            expiresIn = tokenResponse.expiresIn
        )
    }

    override fun refreshAccessToken(refresh: String): AccessTokenResponse? {

        val tokenResponse = service.refreshAccessToken(refresh)

        LogHelper.logD("refreshTokenSuccess: ${tokenResponse.accessToken}, code: $refresh")
        LogHelper.logD("------------------------------")

        return AccessTokenResponse(

            accessToken = tokenResponse.accessToken,
            refreshToken = tokenResponse.refreshToken,
            tokenType = tokenResponse.tokenType,
            expiresIn = tokenResponse.expiresIn
        )
    }
}