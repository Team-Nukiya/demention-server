package team.nukiya.demention.domain.user.domain

import team.nukiya.demention.domain.help.domain.AllHelp
import java.util.UUID

open class UserInformation(
    val userId: UUID,
    val nickName: String,
    val addressName: String,
    val userHelps: List<AllHelp>,
    val helpCount: Long,
    val userSupports: List<AllHelp>,
    val supportCount: Long,
)
