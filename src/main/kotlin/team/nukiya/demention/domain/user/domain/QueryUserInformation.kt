package team.nukiya.demention.domain.user.domain

import com.querydsl.core.annotations.QueryProjection
import java.util.UUID

class QueryUserInformation @QueryProjection constructor(
    userId: UUID,
    nickName: String,
    addressName: String,
    helpCount: Long,
    supportCount: Long,
    point: Double,
) : UserInformation(
    userId = userId,
    nickName = nickName,
    addressName = addressName,
    helpCount = helpCount,
    supportCount = supportCount,
    point = point,
)
