package data.data_source.api

import data.data_source.api.model.ApiResponse

interface ApiInteractor {

    fun get(url: String, headers: Map<String, String> = mapOf()): ApiResponse

    fun post(url: String, body: Any, headers: Map<String, String> = mapOf()): ApiResponse

    fun patch(url: String, body: Any, headers: Map<String, String> = mapOf()): ApiResponse
}