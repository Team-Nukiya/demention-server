package team.nukiya.demention.domain.help.domain

import team.nukiya.demention.domain.help.exception.FailedIdentityVerification
import java.time.LocalDateTime
import java.util.UUID

data class Help(
    val id: UUID = UUID.randomUUID(),
    val userId: UUID,
    val title: String,
    val content: String,
    val compensation: String,
    val helpImageUrl: String,
    val helpStatus: HelpStatus,
    val helpStartDateTime: LocalDateTime,
    val helpEndDateTime: LocalDateTime,
    val createdDateTime: LocalDateTime,
    val modifiedDateTime: LocalDateTime,
) {

    fun verifyIdentityVerification(userId: UUID) {
        if (this.userId != userId) {
            throw FailedIdentityVerification
        }
    }
}
