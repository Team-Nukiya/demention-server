package team.nukiya.demention.domain.help.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import team.nukiya.demention.domain.help.domain.HelpStatus.HELPING
import team.nukiya.demention.domain.help.exception.FailedIdentityVerification
import java.time.LocalDateTime
import java.util.UUID

class HelpTest {

    @Test
    fun `봉사 공고를 작성한 유저이다`() {
        // given
        val userId = UUID.randomUUID()
        val help = Help(
            id = UUID.randomUUID(),
            userId = userId,
            title = "약이 필요합니다.",
            content = "이러이러해서 약이 필요합니다.",
            compensation = "1만 원",
            helpImageUrl = "이미지 링크",
            helpStatus = HELPING,
            helpStartDateTime = LocalDateTime.MIN,
            helpEndDateTime = LocalDateTime.MAX,
            createdDateTime = LocalDateTime.now(),
            modifiedDateTime = LocalDateTime.now(),
        )

        // when & teen
        assertDoesNotThrow {
            help.verifyIdentityVerification(userId)
        }
    }

    @Test
    fun `봉사 공고를 작성한 유저가 아니다`() {
        // given
        val userId = UUID.randomUUID()
        val help = Help(
            id = UUID.randomUUID(),
            userId = UUID.randomUUID(),
            title = "약이 필요합니다.",
            content = "이러이러해서 약이 필요합니다.",
            compensation = "1만 원",
            helpImageUrl = "이미지 링크",
            helpStatus = HELPING,
            helpStartDateTime = LocalDateTime.MIN,
            helpEndDateTime = LocalDateTime.MAX,
            createdDateTime = LocalDateTime.now(),
            modifiedDateTime = LocalDateTime.now(),
        )

        // when & teen
        assertThrows<FailedIdentityVerification> {
            help.verifyIdentityVerification(userId)
        }
    }
}
