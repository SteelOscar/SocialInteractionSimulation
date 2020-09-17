package common

import data.data_source.api.ApiInteractor
import data.data_source.api.deserializer.DataDeserializer
import data.di.DataDeserializerComponentHolder
import okhttp3.Headers
import org.joda.time.DateTime

val dataDeserializer by lazy { DataDeserializerComponentHolder.getComponent().deserializer }

fun Headers.toMap(): Map<String,String> {

    return toMultimap().mapValues { it.value.joinToString(separator = ";") }
}

inline fun <reified T> Any.convertToModel(deserializer: DataDeserializer = dataDeserializer): T {

    return deserializer.convertToModel(this, T::class.java)
}

inline fun <reified T> ApiInteractor.postModel(url: String,
                                               body: Any,
                                               headers: Map<String,String> = mapOf()): T {

    return post(url, body, headers).convertToModel(dataDeserializer)
}

inline fun <reified T> ApiInteractor.getModel(url: String,
                                               headers: Map<String,String> = mapOf()): T {

    return get(url, headers).convertToModel(dataDeserializer)
}

inline fun <reified T> ApiInteractor.patchModel(url: String,
                                               body: Any,
                                               headers: Map<String,String> = mapOf()): T {

    return patch(url, body, headers).convertToModel(dataDeserializer)
}

inline fun <reified T> T?.orEmpty(): T {

    if (this != null) return this

    val asd = T::class.java

    val result =  when(T::class.java) {

        Integer::class.java -> 0
        String::class.java -> ""
        DateTime::class.java -> DateTime()
        Boolean::class.java -> false
        else -> this!!
    }

    return result as T
}
