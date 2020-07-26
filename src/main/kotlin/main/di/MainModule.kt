package main.di

import dagger.Binds
import dagger.Module
import data.data_source.db.neo4j.model.Person
import di.DiProvider
import main.UserProvider

@Module
abstract class MainModule {

    @Binds
    abstract fun bindUserProvider(impl: UserProvider): DiProvider<HashMap<Int, Person>>
}