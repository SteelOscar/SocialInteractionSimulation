package main

import data.data_source.db.model.Person
import di.DaggerAppComponent
import di.DiProvider
import javax.inject.Inject

class SocialInteractionSimulation {

    init {

        DaggerAppComponent.create().inject(this)
    }

    @Inject
    lateinit var userProvider: DiProvider<HashMap<Int, Person>>

    fun startSimulation() {

        val users = userProvider.provide()
    }
}