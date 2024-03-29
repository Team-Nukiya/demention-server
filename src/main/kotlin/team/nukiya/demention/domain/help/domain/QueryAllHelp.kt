package team.nukiya.demention.domain.help.domain

import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime
import java.util.UUID

class QueryAllHelp @QueryProjection constructor(
    helpId: UUID,
    title: String,
    compensation: String,
    helpImageUrl: String,
    helpStartDateTime: LocalDateTime,
    helpEndDateTime: LocalDateTime,
    userAddressName: String,
    userNickName: String,
) : AllHelp(
    helpId = helpId,
    title = title,
    compensation = compensation,
    helpImageUrl = helpImageUrl,
    helpStartDateTime = helpStartDateTime,
    helpEndDateTime = helpEndDateTime,
    userAddressName = userAddressName,
    userNickName = userNickName,
)
