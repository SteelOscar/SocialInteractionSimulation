package data.mapper

import data.data_source.api.model.body.ConversationBody
import domain.model.ConversationDomain
import javax.inject.Inject

class ConversationDomainToBodyMapper @Inject constructor() : (ConversationDomain) -> ConversationBody {

    override fun invoke(domain: ConversationDomain) = with(domain) {

        ConversationBody(
            subject =  subject,
            recipients = recipients,
            message = startMessage
        )
    }
}