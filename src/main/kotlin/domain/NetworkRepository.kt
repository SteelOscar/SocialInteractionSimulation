package domain

import data.data_source.api.model.response.Conversation
import data.data_source.api.model.response.Message
import data.data_source.api.model.response.UserProfile
import domain.model.ConversationDomain
import domain.model.MessageDomain
import domain.model.UpdateUserDomain

interface NetworkRepository {

    fun sendMessage(model: MessageDomain): Message

    fun createConversation(model: ConversationDomain): Conversation

    fun createUser()

    fun getAuthUser(): UserProfile

    fun updateUserProfile(model: UpdateUserDomain): UserProfile
}