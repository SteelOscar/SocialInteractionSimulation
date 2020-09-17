package main

import data.data_source.db.neo4j.model.Person
import di.DaggerAppComponent
import di.DiProvider
import domain.usecase.CreateUserUseCase
import domain.usecase.GetUserInfoUseCase
import domain.usecase.OAuthRegisterUserUseCase
import domain.usecase.RegisterApplicationDiasporaCase
import javax.inject.Inject

class SocialInteractionSimulation {

    init {

        DaggerAppComponent.create().inject(this)
    }

    @Inject
    lateinit var userProvider: DiProvider<HashMap<Int, Person>>

    @Inject
    lateinit var oAuthUseCase: OAuthRegisterUserUseCase

    @Inject
    lateinit var getUserInfoUseCase: GetUserInfoUseCase

    @Inject
    lateinit var createUserUseCase: CreateUserUseCase

    @Inject
    lateinit var registerApplicationDiasporaCase: RegisterApplicationDiasporaCase

    fun startSimulation() {

//        val users = userProvider.provide()

//        LogHelper.logI(getUserInfoUseCase.exec())

//        users.forEach { createUserUseCase.exec(it.value) }

//        createUserUseCase.exec(users.values.first())

        oAuthUseCase.exec(null)
    }
}