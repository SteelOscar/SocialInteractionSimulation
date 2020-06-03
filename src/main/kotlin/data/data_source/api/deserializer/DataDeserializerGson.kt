package data.data_source.api.deserializer

import com.google.gson.GsonBuilder
import java.lang.reflect.Type
import javax.inject.Inject

class DataDeserializerGson @Inject constructor() : DataDeserializer {

    private val gson = GsonBuilder().create()

    override fun <T> convertToModel(obj: Any, type: Type): T {

        return gson.fromJson(gson.toJson(obj), type)
    }
}