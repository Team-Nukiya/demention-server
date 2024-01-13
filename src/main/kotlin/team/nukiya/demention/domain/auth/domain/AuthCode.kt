package team.nukiya.demention.domain.auth.domain

import team.nukiya.demention.domain.auth.exception.WrongAuthCodeException
import kotlin.random.Random

data class AuthCode(
    val code: String,
    val phoneNumber: String,
) {
    fun certifyAuthCode(other: AuthCode) {
        if (this != other) {
            throw WrongAuthCodeException
        }
    }

    companion object {
        fun generateRandomCode() =
            StringBuffer().apply {
                repeat(6) {
                    this.append(Random.nextInt(9))
                }
            }.toString()
    }
}
