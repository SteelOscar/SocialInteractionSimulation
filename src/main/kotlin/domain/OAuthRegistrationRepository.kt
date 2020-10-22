package domain

import data.data_source.api.model.register.OAuthResponse
import data.data_source.api.model.response.AccessTokenResponse
import java.io.File
import java.io.FileWriter

interface OAuthRegistrationRepository {

    val fileLogs: File
    val writerLogs: FileWriter

    fun registerApplication(): OAuthResponse?

    fun getAuthenticateCode(login: String, password: String): String?

    fun getAccessToken(code: String): AccessTokenResponse?

    fun refreshAccessToken(refresh: String): AccessTokenResponse?
}