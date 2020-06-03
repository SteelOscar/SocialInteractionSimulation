package data

import common.getModel
import common.patchModel
import common.postModel
import data.data_source.api.ApiInteractor
import data.data_source.api.model.body.MessageBody
import data.data_source.api.model.response.Conversation
import data.data_source.api.model.response.Message
import data.data_source.api.model.response.UserProfile
import data.mapper.ConversationDomainToBodyMapper
import data.mapper.UpdateUserDomainToBodyMapper
import domain.NetworkRepository
import domain.model.ConversationDomain
import domain.model.MessageDomain
import domain.model.UpdateUserDomain
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(

    private val apiInteractor: ApiInteractor,
    private val conversationMapper: ConversationDomainToBodyMapper,
    private val updateUserMapper: UpdateUserDomainToBodyMapper

) : NetworkRepository {

    override fun sendMessage(model: MessageDomain): Message {

        return apiInteractor.postModel(
            url = "/api/v1/conversations/${model.guid}/messages",
            body = MessageBody(message = model.message)
        )
    }

    override fun createConversation(model: ConversationDomain): Conversation {

        return apiInteractor.postModel(
            url = "/api/v1/conversations",
            body = conversationMapper.invoke(domain = model)
        )
    }

    override fun createUser() {
        /**
         * not implemented in API Diaspora Social Network
         */
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
}