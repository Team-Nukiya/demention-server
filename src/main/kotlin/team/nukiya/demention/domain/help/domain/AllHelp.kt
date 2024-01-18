package team.nukiya.demention.domain.help.domain

import java.time.LocalDateTime
import java.util.UUID

open class AllHelp(
    val id: UUID,
    val title: String,
    val compensation: String,
    val helpImageUrl: String,
    val helpStartDateTime: LocalDateTime,
    val helpEndDateTime: LocalDateTime,
    val userAddressName: String,
    val userNickName: String,
) {
    companion object {
        const val LIMIT = 10L
    }
}
