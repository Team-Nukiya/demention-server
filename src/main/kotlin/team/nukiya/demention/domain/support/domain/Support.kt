package team.nukiya.demention.domain.support.domain

import team.nukiya.demention.domain.support.domain.SupportStatus.SUPPORTING
import team.nukiya.demention.domain.support.exception.SupportCanNotCancelException
import java.time.LocalDateTime
import java.util.UUID

data class Support(
    val id: UUID = UUID.randomUUID(),
    val userId: UUID,
    val helpId: UUID,
    val supportStatus: SupportStatus,
    val createdDateTime: LocalDateTime = LocalDateTime.now(),
) {
    fun checkCancellable() {
        if (this.supportStatus != SUPPORTING) {
            throw SupportCanNotCancelException
        }
    }
}
