package data.di

import dagger.Binds
import dagger.Module
import data.data_source.api.deserializer.DataDeserializer
import data.data_source.api.deserializer.DataDeserializerGson

@Module
abstract class DataDeserializerModule {

    @Binds
    abstract fun bindGSON(impl: DataDeserializerGson): DataDeserializer
}