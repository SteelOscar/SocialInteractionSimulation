package data.data_source.api.model.body

import com.google.gson.annotations.SerializedName

data class UpdateUserBody(

    @SerializedName("bio")
    val bio: String,

    /**
     * Date format ISO 8601
     * Example:
     *  YYYY-MM -> 2005-08
     *  YYYYMMDD -> 20050809
     *  YYYY-MM-DD -> 2005-08-09
     *  and etc...
     */
    @SerializedName("birthday")
    val birthday: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("name")
    val name: String
)