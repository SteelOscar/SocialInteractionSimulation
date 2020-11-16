package common

import common.AppConstant.CURRENT_USER_TOKEN
import data.data_source.api.ApiInteractor
import data.data_source.api.deserializer.DataDeserializer
import data.di.DataDeserializerComponentHolder
import okhttp3.Headers

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

    val tokenHeader = hashMapOf( "Authorization" to "Bearer $CURRENT_USER_TOKEN")

    return post(url, body, tokenHeader.apply { putAll(headers) }).body.convertToModel(dataDeserializer)
}

inline fun <reified T> ApiInteractor.postModelNotAuthorize(url: String,
                                               body: Any,
                                               headers: Map<String,String> = mapOf()): T {

    return post(url, body).body.convertToModel(dataDeserializer)
}

inline fun <reified T> ApiInteractor.getModel(url: String,
                                               headers: Map<String,String> = mapOf()): T {

    val tokenHeader = hashMapOf( "Authorization" to "Bearer $CURRENT_USER_TOKEN")

    return get(url, tokenHeader.apply { putAll(headers) }).body.convertToModel(dataDeserializer)
}

inline fun <reified T> ApiInteractor.patchModel(url: String,
                                               body: Any,
                                               headers: Map<String,String> = mapOf()): T {

    val tokenHeader = hashMapOf( "Authorization" to "Bearer $CURRENT_USER_TOKEN")

    return patch(url, body, tokenHeader.apply { putAll(headers) }).body.convertToModel(dataDeserializer)
}

fun String.convertCyrillic(): String {

    val cyr = arrayOf(
        "а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "й", "к", "л", "м", "н", "о", "п",
        "р", "с", "т", "у", "ф", "х", "ц", "ч", "ю", "я", "э", "ш", "щ", "ь", "ы", "А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З",
        "И", "К", "Л", "М", "Н", "О", "П", "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ю", "Я", "Э", "Ш", "Щ", "Ы"
    )
    val lat = arrayOf(
        "a", "b", "v", "g", "d", "e", "e", "zh", "z", "i", "y", "k", "l", "m", "n", "o", "p",
        "r", "s", "t", "u", "f", "h", "ts", "ch", "yu", "ya", "eh", "sh", "shch", "", "i", "A", "B", "V", "G", "D", "E", "E", "Zh", "Z",
        "I", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "F", "H", "Ts", "Ch", "Yu", "Ya", "Eh", "Sh", "Shch", "I"
    )

    var formattedString = this

    cyr.forEachIndexed { index, c -> formattedString = formattedString.replace(c, lat[index]) }

    return formattedString
}