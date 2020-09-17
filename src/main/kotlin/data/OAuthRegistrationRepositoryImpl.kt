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
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL
import javax.inject.Inject

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

        val driver = FirefoxDriver()

        driver.get(AppConstant.LOGIN_URL)

        val loginView = driver.findElementById("user_username")
        val passwordView = driver.findElementById("user_password")
        val loginButtonView = driver.findElementsByName("commit")[0]

        loginView.sendKeys("RenatYumagulov")
        passwordView.sendKeys("123456")
        loginButtonView.click()

        driver.navigate().to(AppConstant.AUTHENTICATION_CODE_URL)

        val approveButtonView = driver.findElements(By.className("approval-button"))[1]

        kotlin.runCatching { approveButtonView.click() }.onFailure {

            println(it.message)
        }

        driver.quit()

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