package domain.model

data class ConversationDomain(

    val subject: String,
    val recipients: List<String>,
    val startMessage: String
)