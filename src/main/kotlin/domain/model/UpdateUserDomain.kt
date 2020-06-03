package domain.model

data class UpdateUserDomain(

    val bio: String,
    val birthday: String,
    val gender: String,
    val name: String
)