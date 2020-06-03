package data.di

import dagger.Component
import data.data_source.api.deserializer.DataDeserializer

@Component(
    modules = [
        DataDeserializerModule::class
    ]
)
interface DataDeserializerComponent {

    val deserializer: DataDeserializer
}