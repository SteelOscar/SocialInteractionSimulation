package data.data_source.api

import data.data_source.api.mapper.RetrofitResponseToApiMapper
import data.data_source.api.model.ApiResponse
import de.undercouch.gradle.tasks.download.org.apache.http.HttpException
import di.DiProvider
import javax.inject.Inject

class ApiInteractorImpl @Inject constructor(

    socialApiProvider: DiProvider<SocialAPI>,

    private val responseMapper: RetrofitResponseToApiMapper

) : ApiInteractor {

    private val apiMethods = socialApiProvider.provide()

    override fun get(url: String, headers: Map<String, String>): ApiResponse {

        val response = apiMethods.get(url, headers).execute()

        if (response.isSuccessful.not()) throw HttpException()

        return responseMapper.invoke(response)
    }

    override fun post(url: String, body: Any, headers: Map<String, String>): ApiResponse {

        val response = apiMethods.post(url, body, headers).execute()

        if (response.isSuccessful.not()) throw HttpException()

        return responseMapper.invoke(response)
    }

    override fun patch(url: String, body: Any, headers: Map<String, String>): ApiResponse {

        val response = apiMethods.patch(url, body, headers).execute()

        if (response.isSuccessful.not()) throw HttpException()

        return responseMapper.invoke(response)
    }
}