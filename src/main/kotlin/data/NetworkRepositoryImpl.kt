package data

import common.AppConstant
import common.getModel
import common.patchModel
import common.postModel
import data.data_source.api.ApiInteractor
import data.data_source.api.model.body.AspectBody
import data.data_source.api.model.body.CreateContactBody
import data.data_source.api.model.body.MessageBody
import data.data_source.api.model.body.PostBody
import data.data_source.api.model.response.AspectResponse
import data.data_source.api.model.response.Conversation
import data.data_source.api.model.response.Message
import data.data_source.api.model.response.UserProfile
import data.data_source.db.neo4j.model.Person
import data.mapper.ConversationDomainToBodyMapper
import data.mapper.UpdateUserDomainToBodyMapper
import domain.NetworkRepository
import domain.model.ConversationDomain
import domain.model.MessageDomain
import domain.model.PostDomain
import domain.model.UpdateUserDomain
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(

    private val apiInteractor: ApiInteractor,
    private val conversationMapper: ConversationDomainToBodyMapper,
    private val updateUserMapper: UpdateUserDomainToBodyMapper,
    private val sshInteractor: SshInteractor

) : NetworkRepository {

    override fun sendMessage(model: MessageDomain) {

        runCatching {

            apiInteractor.post(
                url = "/api/v1/conversations/${model.guid}/messages",
                body = MessageBody(message = model.message),
                headers = hashMapOf(
                    "SenderId" to model.senderId,
                    "RecipientId" to model.recipientId,
                    "Authorization" to "Bearer ${AppConstant.CURRENT_USER_TOKEN}"
                )
            )
        }
    }

    override fun createConversation(model: ConversationDomain): Conversation {

        return apiInteractor.postModel(
            url = "/api/v1/conversations",
            body = conversationMapper.invoke(domain = model)
        )
    }

    override fun createAspect(body: AspectBody): AspectResponse {

        return apiInteractor.postModel(
            url = "/api/v1/aspects",
            body =  body
        )
    }

    override fun createContact(aspectId: Int, body: CreateContactBody) {

        runCatching {

            apiInteractor.post(
                url = "/api/v1/aspects/$aspectId/contacts",
                body = body,
                headers = hashMapOf( "Authorization" to "Bearer ${AppConstant.CURRENT_USER_TOKEN}")
            )
        }
    }

    override fun createUser(user: Person) {
        /**
         * not implemented in API Diaspora Social Network
         */

        val password = "qazwsxed9"

        sshInteractor.createUser(user, password)
    }

    override fun getAuthUser(): UserProfile {

        return apiInteractor.getModel(url = "/api/v1/user")
    }

    override fun updateUserProfile(model: UpdateUserDomain): UserProfile {

        return apiInteractor.patchModel(
            url = "/api/v1/user",
            body = updateUserMapper.invoke(domain = model)
        )
    }

    override fun sendPost(model: PostDomain) {

        runCatching {

            val tokenHeader = hashMapOf("Authorization" to "Bearer ${AppConstant.CURRENT_USER_TOKEN}")

            tokenHeader["SenderId"] = model.senderId

            apiInteractor.post(
                url = "/api/v1/posts",
                body = PostBody(

                    body = model.message,
                    aspects = listOf(model.aspect)
                ),
                headers = tokenHeader
            )
        }
    }
}