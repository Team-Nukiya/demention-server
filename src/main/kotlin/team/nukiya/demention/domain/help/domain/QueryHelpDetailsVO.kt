package team.nukiya.demention.domain.help.domain

import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime
import java.util.UUID

class QueryHelpDetailsVO @QueryProjection constructor(
    id: UUID,
    title: String,
    content: String,
    compensation: String,
    helpImageUrl: String,
    helpStatus: HelpStatus,
    helpStartDateTime: LocalDateTime,
    helpEndDateTime: LocalDateTime,
    modifiedDateTime: LocalDateTime,
    userAddressName: String,
    userNickName: String,
) : HelpDetails(
    id = id,
    title = title,
    content = content,
    compensation = compensation,
    helpImageUrl = helpImageUrl,
    helpStatus = helpStatus,
    helpStartDateTime = helpStartDateTime,
    helpEndDateTime = helpEndDateTime,
    modifiedDateTime = modifiedDateTime,
    userAddressName = userAddressName,
    userNickName = userNickName,
)
