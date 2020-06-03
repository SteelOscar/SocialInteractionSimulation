package data.data_source.api

import data.data_source.api.model.body.ConversationBody
import data.data_source.api.model.body.MessageBody
import data.data_source.api.model.body.UpdateUserBody
import data.data_source.api.model.response.Conversation
import data.data_source.api.model.response.Message
import data.data_source.api.model.response.UserProfile
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url

interface SocialAPI {

    @POST
    fun post(@Url url: String, @Body body: Any, @HeaderMap headers: Map<String, String>): Call<ResponseBody>

    @GET
    fun get(@Url url: String, @HeaderMap headers: Map<String, String>): Call<ResponseBody>

    @PATCH
    fun patch(@Url url: String, @Body body: Any, @HeaderMap headers: Map<String, String>): Call<ResponseBody>

    @Deprecated("use post, get, patch")
    @POST("/api/v1/conversations/{guid}/messages")
    fun sendMessage(

        @Path("guid") guid: String,
        @Body messageBody: MessageBody

    ) : Call<Message>

    @Deprecated("use post, get, patch")
    @POST("/api/v1/conversations")
    fun createConversation(

        @Body conversationBody: ConversationBody

    ): Call<Conversation>

    /**
     * Diaspora api hasn't implementation of this method
     */
    @Deprecated("use post, get, patch")
    @POST("/end_point_create_user")
    fun createUser()

    @Deprecated("use post, get, patch")
    @GET("/api/v1/user")
    fun getAuthUser(): Call<UserProfile>

    @Deprecated("use post, get, patch")
    @PATCH("/api/v1/user")
    fun updateUserProfile(

        @Body updateUserBody: UpdateUserBody

    ): Call<UserProfile>
}