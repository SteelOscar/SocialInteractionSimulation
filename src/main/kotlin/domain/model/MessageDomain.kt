package domain.model

data class MessageDomain(

    val guid: String,
    val senderId: String,
    val recipientId: String,
    val message: String
)