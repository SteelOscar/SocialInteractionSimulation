package data.data_source.api.model.body

import com.google.gson.annotations.SerializedName

class PostBody(

    @SerializedName("body")
    val body: String,

    @SerializedName("public")
    val public: Boolean = true,

    @SerializedName("aspects")
    val aspects: List<Int>
)