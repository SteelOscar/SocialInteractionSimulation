package data.data_source.api

import common.AppConstant
import di.DiProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class SocialApiProvider @Inject constructor(): DiProvider<SocialAPI> {

    override fun provide(): SocialAPI {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(AppConstant.BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SocialAPI::class.java)
    }
}