package team.nukiya.demention.domain.help.domain

import java.time.LocalDateTime
import java.util.UUID

open class HelpDetails(
    val id: UUID,
    val title: String,
    val content: String,
    val compensation: String,
    val helpImageUrl: String,
    val helpStatus: HelpStatus,
    val helpStartDateTime: LocalDateTime,
    val helpEndDateTime: LocalDateTime,
    val modifiedDateTime: LocalDateTime,
    val userAddressName: String,
    val userNickName: String,
)