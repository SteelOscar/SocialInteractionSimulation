package main

import domain.model.MessageDomain
import domain.model.PostDomain
import java.util.Date

sealed class MessageEvent(

    open val time: Date

) {

    data class PublicPostEvent(

        override val time: Date,

        val message: PostDomain,
        val token: String

    ) : MessageEvent(time)

    data class RefreshTokens(

        override val time: Date

    ) : MessageEvent(time)

    data class NewAccessTokens(

        override val time: Date

    ) : MessageEvent(time)

    data class ConversationMessageEvent(

        override val time: Date,

        val message: MessageDomain,
        val token: String

    ) : MessageEvent(time)
}