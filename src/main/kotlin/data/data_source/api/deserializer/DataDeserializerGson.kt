package data.data_source.api.deserializer

import com.google.gson.GsonBuilder
import java.lang.reflect.Type
import javax.inject.Inject

class DataDeserializerGson @Inject constructor() : DataDeserializer {

    private val gson = GsonBuilder().setLenient().create()

    override fun <T> convertToModel(obj: Any, type: Type): T {

        return when(obj) {

            is String -> {

                gson.fromJson(obj, type)
            }
            else -> gson.fromJson(gson.toJson(obj), type)
        }
    }
}