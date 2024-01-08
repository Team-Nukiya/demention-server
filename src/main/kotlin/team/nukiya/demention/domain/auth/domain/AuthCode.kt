package team.nukiya.demention.domain.auth.domain

data class AuthCode(
    val code: String,
    val phoneNumber: String,
)
