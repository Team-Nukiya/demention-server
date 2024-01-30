package team.nukiya.demention.domain.help.domain

import team.nukiya.demention.domain.help.domain.HelpStatus.HELPING
import team.nukiya.demention.domain.help.exception.FailedIdentityVerification
import team.nukiya.demention.domain.support.exception.MySupportCanNotApplyException
import java.time.LocalDateTime
import java.util.UUID

data class Help(
    val id: UUID = UUID.randomUUID(),
    val userId: UUID,
    val title: String,
    val content: String,
    val compensation: String,
    val helpImageUrl: String,
    val helpStatus: HelpStatus = HELPING,
    val helpStartDateTime: LocalDateTime,
    val helpEndDateTime: LocalDateTime,
    val createdDateTime: LocalDateTime = LocalDateTime.now(),
    val modifiedDateTime: LocalDateTime = LocalDateTime.now(),
) {
    fun verifyIdentityVerification(userId: UUID) {
        if (this.userId != userId) {
            throw FailedIdentityVerification
        }
    }

    fun checkMine(userId: UUID) {
        if (this.userId == userId) {
            throw MySupportCanNotApplyException
        }
    }
}
