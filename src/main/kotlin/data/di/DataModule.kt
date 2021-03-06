package data.di

import dagger.Binds
import dagger.Module
import data.Neo4jRepositoryImpl
import data.NetworkRepositoryImpl
import data.OAuthRegistrationRepositoryImpl
import data.data_source.api.ApiInteractor
import data.data_source.api.ApiInteractorImpl
import data.data_source.api.SocialAPI
import data.data_source.api.SocialApiProvider
import data.data_source.db.postgres.PostgresInteractor
import data.data_source.db.postgres.PostgresInteractorImpl
import di.DiProvider
import domain.Neo4jRepository
import domain.NetworkRepository
import domain.OAuthRegistrationRepository

@Module
abstract class DataModule {

    @Binds
    abstract fun bindApiProvider(impl: SocialApiProvider): DiProvider<SocialAPI>

    @Binds
    abstract fun bindApiInteractor(impl: ApiInteractorImpl): ApiInteractor

    @Binds
    abstract fun bindPostgresInteractor(impl: PostgresInteractorImpl): PostgresInteractor

    @Binds
    abstract fun bindNeo4jRepository(impl: Neo4jRepositoryImpl): Neo4jRepository

    @Binds
    abstract fun bindOAuthRepository(impl: OAuthRegistrationRepositoryImpl): OAuthRegistrationRepository

    @Binds
    abstract fun bindNetworkRepository(impl: NetworkRepositoryImpl): NetworkRepository
}