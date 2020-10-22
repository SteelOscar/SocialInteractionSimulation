package data.data_source.api.model

data class ApiResponse(

    val code: Int,
    val isSuccessful: Boolean,
    val body: String,
    val headers: Map<String, String>,
    val message: String,
    val protocol: String,
    val receivedResponseAtMillis: Long,
    val sentRequestAtMillis: Long
)