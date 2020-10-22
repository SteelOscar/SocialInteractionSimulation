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
import org.openqa.selenium.firefox.FirefoxDriver
import java.io.File
import java.io.FileWriter
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
    override val fileLogs = File("/home/renat/Desktop/social network interaction/logs.txt")
    override val writerLogs = FileWriter(fileLogs, false)

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

        runCatching {

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

                driver.quit()

                writerLogs.write("authCode:secondCatch ${it.message}\n")

                return it.message
                    ?.substringAfter("code%3D")
                    ?.substringBefore("&c=").orEmpty()
            }
        }.onFailure { writerLogs.write("authCode:firstCatch ${it.message}\n") }

        return null
    }

    override fun getAccessToken(code: String): AccessTokenResponse? {

        runCatching { service.getAccessToken(code) }.onFailure {


            writerLogs.write("accessCode:firstCatch ${it.message}\n")

            runCatching {

                LogHelper.logD(it.message.orEmpty())

                val tokenResponse = it.message
                    ?.substringAfter(": '")
                    ?.removeSuffix("'")
                    .orEmpty()

                val response = tokenResponse.convertToModel<AccessTokenResponse>()

                println("tokenResponse: $response")

                return response
            }.onFailure { writerLogs.write("accessCode:secondCatch ${it.message}\n") }
        }

        return null
    }

    override fun refreshAccessToken(refresh: String): AccessTokenResponse? {

        runCatching { service.refreshAccessToken(refresh) }.onFailure {

            writerLogs.write("refreshAccess:firstCatch ${it.message}\n")

            runCatching {

                val tokenResponse = it.message
                    ?.substringAfter(": '")
                    ?.removeSuffix("'")
                    .orEmpty()

                val response = tokenResponse.convertToModel<AccessTokenResponse>()

                println("tokenResponse: $response")

                return response
            }.onFailure {

                writerLogs.write("refreshAccess:firstCatch ${it.message}\n")
                return null
            }
        }

        return null
    }
}