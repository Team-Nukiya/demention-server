package team.nukiya.demention.domain.auth.domain

import team.nukiya.demention.domain.auth.exception.WrongAuthCodeException

data class AuthCode(
    val code: String,
    val phoneNumber: String,
) {

    fun certifyAuthCode(other: AuthCode) {
        if (this != other) {
            throw WrongAuthCodeException
        }
    }
}
