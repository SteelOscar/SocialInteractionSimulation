package domain.model

data class UpdateUserDomain(

    val bio: String? = null,
    val birthday: String? = null,
    val gender: String? = null,
    val name: String? = null,
    val publicProfileInfo: Boolean = true
)