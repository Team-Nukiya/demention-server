package team.nukiya.demention.domain.user.domain

import java.util.UUID

open class UserInformation(
    val userId: UUID,
    val nickName: String,
    val addressName: String,
    val helpCount: Long,
    val supportCount: Long,
    val point: Double,
)
