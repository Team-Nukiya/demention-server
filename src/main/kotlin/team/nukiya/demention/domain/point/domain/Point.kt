package team.nukiya.demention.domain.point.domain

import java.time.LocalDateTime
import java.util.UUID

data class Point(
    val id: UUID = UUID.randomUUID(),
    val giveUserId: UUID,
    val receiveSupportId: UUID,
    val point: Float,
    val createdDateTime: LocalDateTime,
)
