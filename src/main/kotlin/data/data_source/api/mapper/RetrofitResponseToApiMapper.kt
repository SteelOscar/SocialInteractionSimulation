package data.data_source.api.mapper

import common.toMap
import data.data_source.api.model.ApiResponse
import okhttp3.Headers
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class RetrofitResponseToApiMapper @Inject constructor() : (Response<ResponseBody>) -> ApiResponse {

    override fun invoke(response: Response<ResponseBody>): ApiResponse {

        val raw = response.raw()

        return ApiResponse(

            code = raw.code(),
            isSuccessful = raw.isSuccessful,
            body = response.body()?.string().orEmpty(),
            headers = raw.headers().toMap(),
            message = raw.message(),
            protocol = raw.protocol().name,
            receivedResponseAtMillis = raw.receivedResponseAtMillis(),
            sentRequestAtMillis = raw.sentRequestAtMillis()
        )
    }
}