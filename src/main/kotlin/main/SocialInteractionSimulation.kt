package main

import common.AppConstant
import common.LogHelper
import data.data_source.db.neo4j.model.Person
import di.DaggerAppComponent
import di.DiProvider
import domain.model.ConversationDomain
import domain.usecase.CreateAspectUseCase
import domain.usecase.CreateContactUseCase
import domain.usecase.CreateConversationUseCase
import domain.usecase.CreateUserUseCase
import domain.usecase.GetUserInfoUseCase
import domain.usecase.RefreshAccessTokenUseCase
import domain.usecase.RegisterApplicationDiasporaCase
import domain.usecase.SendMessageUseCase
import domain.usecase.TruncateUsersCase
import domain.usecase.UpdateTokensExpiresCase
import javax.inject.Inject

class SocialInteractionSimulation {

    init {

        DaggerAppComponent.create().inject(this)
    }

    @Inject
    lateinit var userProvider: DiProvider<HashMap<Int, Person>>

    @Inject
    lateinit var getUserInfoUseCase: GetUserInfoUseCase

    @Inject
    lateinit var createUserUseCase: CreateUserUseCase

    @Inject
    lateinit var truncateUsersCase: TruncateUsersCase

    @Inject
    lateinit var createConversationUseCase: CreateConversationUseCase

    @Inject
    lateinit var createAspectUseCase: CreateAspectUseCase

    @Inject
    lateinit var createContactUseCase: CreateContactUseCase

    @Inject
    lateinit var messageEventSender: MessageEventSender

    @Inject
    lateinit var sendMessageUseCase: SendMessageUseCase

    @Inject
    lateinit var refreshAccessTokenUseCase: RefreshAccessTokenUseCase

    @Inject
    lateinit var updateTokensExpiresCase: UpdateTokensExpiresCase

    @Inject
    lateinit var registerApplicationDiasporaCase: RegisterApplicationDiasporaCase

    fun startSimulation() {

        truncateUsersCase.execute()
        registerApplicationDiasporaCase.execute()

        val usersMap = userProvider.provide()
        val users = usersMap.values.toList()

        users.forEach {

            createUserUseCase.exec(it)
            createAspectUseCase.execute(it)
        }

        updateTokensExpiresCase.execute()

        users.forEach { mainUser ->

            AppConstant.CURRENT_USER_TOKEN = mainUser.authToken

            users.forEach sub@{

                if (it.diasporaId == mainUser.diasporaId) return@sub

                LogHelper.logD("mainUser: $mainUser, contact: $it")
                createContactUseCase.execute(mainUser.aspectId!!, it.diasporaId!!)
            }
        }

        users.forEach { person ->

            AppConstant.CURRENT_USER_TOKEN = person.authToken.orEmpty()

            person.relationshipIds.keys.forEach { userId ->

                val user = users.find { it.id == userId }

                if (user?.relationshipIds?.get(person.id).isNullOrBlank()) {

                    val conversation = createConversationUseCase.execute(
                        ConversationDomain(
                            subject = "Conversation ${person.name} and ${user?.name}",
                            recipients = listOf(
                                user?.diasporaId!!
                            ),
                            startMessage = "Hello! ${user.name}"
                        )
                    )

                    person.relationshipIds[user.id] = conversation.guid
                    user.relationshipIds[person.id] = conversation.guid
                }
            }
        }

        LogHelper.logSeparator()
        LogHelper.logSeparator()
        LogHelper.logSeparator()
        LogHelper.logD("count = ${usersMap.keys.count()}")
        LogHelper.logSeparator()

        messageEventSender.generateAndSendMessageEvents(usersMap)
    }
}