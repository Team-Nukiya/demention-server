package team.nukiya.demention.domain.user.domain

import java.util.UUID

data class User(
    val id: UUID,
    val phoneNumber: String,
    val nickName: String,
    val name: String?,
    val area: String,
    val authority: Authority,
    val isDeleted: Boolean,
)
