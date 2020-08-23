package main

import data.data_source.db.neo4j.model.Person
import di.DaggerAppComponent
import di.DiProvider
import domain.usecase.CreateUserUseCase
import domain.usecase.GetUserInfoUseCase
import domain.usecase.OAuthUseCase
import javax.inject.Inject

class SocialInteractionSimulation {

    init {

        DaggerAppComponent.create().inject(this)
    }

    @Inject
    lateinit var userProvider: DiProvider<HashMap<Int, Person>>

    @Inject
    lateinit var oAuthUseCase: OAuthUseCase

    @Inject
    lateinit var getUserInfoUseCase: GetUserInfoUseCase

    @Inject
    lateinit var createUserUseCase: CreateUserUseCase

    fun startSimulation() {

        val users = userProvider.provide()

//        LogHelper.logI(getUserInfoUseCase.exec())

        users.forEach { createUserUseCase.exec(it.value) }

    }
}