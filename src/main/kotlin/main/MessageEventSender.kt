package main

import common.AppConstant
import common.LogHelper
import data.data_source.db.neo4j.model.Person
import domain.usecase.OAuthRegisterUserUseCase
import domain.usecase.RefreshAccessTokenUseCase
import domain.usecase.SendMessageUseCase
import domain.usecase.SendPostUseCase
import java.io.File
import java.io.FileWriter
import java.util.Date
import javax.inject.Inject

class MessageEventSender @Inject constructor(

    private val messageEventCreator: MessageEventCreator,
    private val sendMessageUseCase: SendMessageUseCase,
    private val sendPostUseCase: SendPostUseCase,
    private val refreshAccessTokenUseCase: RefreshAccessTokenUseCase,
    private val oAuthRegisterUserUseCase: OAuthRegisterUserUseCase

) {

    fun generateAndSendMessageEvents(users: Map<Int, Person>) {

        messageEventCreator.setUsers(users)

//        Runtime.getRuntime().exec("sudo wireshark -i ens38 -Y http -k")
//
        val fileEvents = File("/home/renat/Desktop/social network interaction/events.txt")
        val writer = FileWriter(fileEvents, false)

        messageEventCreator.getMessageEvents().forEach {

            writer.write("$it\n")
        }
        writer.close()
//
//        val fileUsers = File("/home/renat/Desktop/social network interaction/users.txt")
//        val writerUsers = FileWriter(fileUsers, false)
//
//        users.forEach { (i, person) -> writerUsers.write("id: $i, person: $person\n") }
//        writerUsers.close()
//
//        messageEventCreator.getMessageEvents().forEach {
//
//            while (true) {
//
//                if (Date().time > it.time.time) {
//
//                    when(it) {
//
//                        is MessageEvent.ConversationMessageEvent -> {
//
//                            LogHelper.logD("Send message: $it")
//
//                            val token = users.values.find { person ->
//                                person.diasporaId == it.message.senderId
//                            }?.authToken
//
//                            AppConstant.CURRENT_USER_TOKEN = token!!
//                            sendMessageUseCase.execute(it.message)
//                        }
//                        is MessageEvent.RefreshTokens -> {
//
//                            LogHelper.logD("Refresh all tokens: $it")
//
//                            users.values.forEach { person ->
//
//                                refreshAccessTokenUseCase.exec(person)
//                            }
//                        }
//                        is MessageEvent.NewAccessTokens -> {
//
//                            LogHelper.logD("Generating new access tokens: $it")
//
//                            users.values.forEach { person ->
//
//                                oAuthRegisterUserUseCase.exec(person)
//                            }
//                        }
//                        is MessageEvent.PublicPostEvent -> {
//
//                            LogHelper.logD("Send post: $it")
//
//                            val token = users.values.find { person ->
//                                person.diasporaId == it.message.senderId
//                            }?.authToken
//
//                            AppConstant.CURRENT_USER_TOKEN = token!!
//                            sendPostUseCase.execute(it.message)
//                        }
//                    }
//
//                    break
//                }
//            }
//        }
    }
}