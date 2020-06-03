package data.di

import dagger.Module
import dagger.Provides
import data.data_source.db.Neo4jDeserialization
import data.data_source.db.Neo4jDriverManager

@Module
class DataModule {

    @Provides
    fun provideNeo4jDriverManager() = Neo4jDriverManager()

    @Provides
    fun provideNeo4jDeserialization() = Neo4jDeserialization()
}