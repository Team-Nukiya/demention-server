package team.nukiya.demention.domain.user.domain

import com.querydsl.core.annotations.QueryProjection
import team.nukiya.demention.domain.help.domain.AllHelp
import java.util.UUID

class QueryUserInformation @QueryProjection constructor(
    userId: UUID,
    nickName: String,
    addressName: String,
    userHelps: List<AllHelp>,
    helpCount: Long,
    userSupports: List<AllHelp>,
    supportCount: Long,
) : UserInformation(
    userId = userId,
    nickName = nickName,
    addressName = addressName,
    userHelps = userHelps,
    helpCount = helpCount,
    userSupports = userSupports,
    supportCount = supportCount,
)
