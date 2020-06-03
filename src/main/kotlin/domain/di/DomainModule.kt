package domain.di

import dagger.Module
import dagger.Provides
import data.Neo4jRepositoryImpl
import data.data_source.db.Neo4jDeserialization
import data.data_source.db.Neo4jDriverManager
import domain.Neo4jRepository

@Module
class DomainModule {

    @Provides
    fun provideNeo4jRepository(neo4j: Neo4jDriverManager, neo4jDeserialization: Neo4jDeserialization): Neo4jRepository {

        return Neo4jRepositoryImpl(neo4j, neo4jDeserialization)
    }
}