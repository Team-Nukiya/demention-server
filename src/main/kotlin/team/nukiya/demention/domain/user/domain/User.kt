package team.nukiya.demention.domain.user.domain

import team.nukiya.demention.domain.user.domain.Authority.USER
import java.time.LocalDateTime
import java.util.UUID

data class User(
    val id: UUID = UUID.randomUUID(),
    val phoneNumber: String,
    val nickName: String,
    val name: String? = null,
    val address: Address,
    val authority: Authority = USER,
    val deletedDateTime: LocalDateTime? = null,
) {
    companion object {
        const val NICK_NAME_LENGTH = 10

        fun generateRandomNickName(): String {
            val charSet = ('a'..'z') + ('A'..'Z') + ('0'..'9')

            return (1..NICK_NAME_LENGTH)
                .map { charSet.random() }
                .joinToString("")
        }
    }
}
