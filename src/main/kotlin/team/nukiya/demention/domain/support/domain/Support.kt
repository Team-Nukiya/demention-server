package team.nukiya.demention.domain.support.domain

import java.time.LocalDateTime
import java.util.UUID

data class Support(
    val id: UUID = UUID.randomUUID(),
    val userId: UUID,
    val helpId: UUID,
    val supportStatus: SupportStatus,
    val createdDateTime: LocalDateTime,
)
