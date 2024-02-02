package team.nukiya.demention.domain.point.controller.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import team.nukiya.demention.domain.point.domain.Point
import java.time.LocalDateTime
import java.util.UUID

data class GivePointRequest(
    @NotNull
    val receiveSupportId: UUID,

    @NotNull
    @Min(1)
    @Max(5)
    val point: Float,
) {
    fun toPoint(giveUserId: UUID): Point =
        Point(
            id = UUID.randomUUID(),
            giveUserId = giveUserId,
            receiveSupportId = receiveSupportId,
            point = point,
            createdDateTime = LocalDateTime.now(),
        )
}
