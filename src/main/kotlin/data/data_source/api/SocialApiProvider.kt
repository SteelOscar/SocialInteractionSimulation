package data.data_source.api

import common.AppConstant
import di.DiProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class SocialApiProvider @Inject constructor(): DiProvider<SocialAPI> {

    override fun provide(): SocialAPI {

        return Retrofit.Builder()
            .baseUrl(AppConstant.BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SocialAPI::class.java)
    }
}