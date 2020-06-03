package data.data_source.api.deserializer

import java.lang.reflect.Type

interface DataDeserializer {

    fun <T> convertToModel(obj: Any, type: Type): T
}