package data.data_source.api

import com.github.scribejava.core.extractors.TokenExtractor
import com.github.scribejava.core.model.OAuth2AccessToken
import com.github.scribejava.core.model.Response
import common.convertToModel
import data.data_source.api.model.response.AccessTokenResponse

object DiasporaApiTokenExtractor : TokenExtractor<OAuth2AccessToken> {

    override fun extract(p0: Response?): OAuth2AccessToken {

        val response = p0?.body?.convertToModel<AccessTokenResponse>()
        return OAuth2AccessToken(response?.accessToken, response?.tokenType, response?.expiresIn, response?.refreshToken, "scope", p0?.body)
    }
}