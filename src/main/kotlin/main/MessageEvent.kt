package main

import domain.model.MessageDomain
import java.util.Date

sealed class MessageEvent(

    val time: Date

) {

    class PublicPostEvent(

        time: Date,

        val token: String

    ) : MessageEvent(time)

    class RefreshTokens(

        time: Date

    ) : MessageEvent(time)

    class NewAccessTokens(

        time: Date

    ) : MessageEvent(time)

    class ConversationMessageEvent(

        time: Date,

        val message: MessageDomain,
        val token: String

    ) : MessageEvent(time)
}