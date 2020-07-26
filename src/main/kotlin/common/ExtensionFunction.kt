package common

import data.data_source.api.ApiInteractor
import data.data_source.api.deserializer.DataDeserializer
import data.di.DataDeserializerComponentHolder
import okhttp3.Headers
import org.joda.time.DateTime

fun Headers.toMap(): Map<String,String> {

    return toMultimap().mapValues { it.value.joinToString(separator = ";") }
}

inline fun <reified T> Any.convertToModel(deserializer: DataDeserializer): T {

    return deserializer.convertToModel(this, this::class.java)
}

inline fun <reified T> ApiInteractor.postModel(url: String,
                                               body: Any,
                                               headers: Map<String,String> = mapOf()): T {

    val component = DataDeserializerComponentHolder.getComponent()

    return post(url, body, headers).convertToModel(component.deserializer)
}

inline fun <reified T> ApiInteractor.getModel(url: String,
                                               headers: Map<String,String> = mapOf()): T {

    val component = DataDeserializerComponentHolder.getComponent()

    return get(url, headers).convertToModel(component.deserializer)
}

inline fun <reified T> ApiInteractor.patchModel(url: String,
                                               body: Any,
                                               headers: Map<String,String> = mapOf()): T {

    val component = DataDeserializerComponentHolder.getComponent()

    return patch(url, body, headers).convertToModel(component.deserializer)
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
