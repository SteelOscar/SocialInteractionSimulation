package data.data_source.api

import com.github.scribejava.core.builder.api.DefaultApi20
import com.github.scribejava.core.extractors.OAuth2AccessTokenExtractor
import com.github.scribejava.core.extractors.TokenExtractor
import com.github.scribejava.core.model.OAuth2AccessToken
import com.github.scribejava.core.model.Verb
import common.AppConstant

class DiasporaApi : DefaultApi20() {

    companion object {

        val instance = DiasporaApi()
    }

    override fun getAuthorizationBaseUrl(): String {

        return AppConstant.AUTHENTICATION_CODE_URL
    }

    override fun getAccessTokenEndpoint(): String {

        return AppConstant.ACCESS_TOKEN_URL
    }

    override fun getAccessTokenVerb(): Verb {
        return Verb.POST
    }

    override fun getAccessTokenExtractor(): TokenExtractor<OAuth2AccessToken> {
        return DiasporaApiTokenExtractor
    }
}