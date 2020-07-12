package main

import common.LogHelper
import data.data_source.db.model.Person
import di.DaggerAppComponent
import di.DiProvider
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

    fun startSimulation() {

//        val users = userProvider.provide()

        LogHelper.logI(getUserInfoUseCase.exec())
    }
}