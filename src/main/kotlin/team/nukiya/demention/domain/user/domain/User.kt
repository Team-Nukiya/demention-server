package team.nukiya.demention.domain.user.domain

import team.nukiya.demention.domain.user.domain.Authority.USER
import java.util.UUID

data class User(
    val id: UUID = UUID.randomUUID(),
    val phoneNumber: String,
    val nickName: String,
    val name: String? = null,
    val address: Address,
    val authority: Authority = USER,
    val isDeleted: Boolean = false,
)
