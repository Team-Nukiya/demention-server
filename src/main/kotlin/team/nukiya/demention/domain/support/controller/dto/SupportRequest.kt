package team.nukiya.demention.domain.support.controller.dto

import team.nukiya.demention.domain.support.domain.Support
import team.nukiya.demention.domain.support.domain.SupportStatus.SUPPORTED
import java.util.UUID

data class SupportRequest(
    val helpId: UUID,
) {
    fun toSupport(userId: UUID, helpId: UUID) =
        Support(
            userId = userId,
            helpId = helpId,
            supportStatus = SUPPORTED,
        )
}
