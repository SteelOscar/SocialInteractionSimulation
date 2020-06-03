package main

import common.LogHelper
import data.data_source.db.model.Person
import di.DiProvider
import domain.usecase.GetUsersUseCase
import javax.inject.Inject

class UserProvider @Inject constructor(

    private val getUsersFromDatabaseUseCase: GetUsersUseCase

) : DiProvider<HashMap<Int, Person>> {

    override fun provide(): HashMap<Int, Person> {

        val persons = getUsersFromDatabaseUseCase.getUsers()
        val socialRoleSet = HashSet<String>()
        val professionalRoleSet = HashSet<String>()

        persons.values.map {

            socialRoleSet.addAll(it.socialRole)
            professionalRoleSet.add(it.profRole)
        }

        LogHelper.logI("Person:")
        LogHelper.logSeparator()
        persons.values.map(LogHelper::logD)
        LogHelper.logSeparator()
        LogHelper.logI("Social role Set: $socialRoleSet")
        LogHelper.logI("Professional role Set: $professionalRoleSet")

        getUsersFromDatabaseUseCase.close()

        return persons
    }
}