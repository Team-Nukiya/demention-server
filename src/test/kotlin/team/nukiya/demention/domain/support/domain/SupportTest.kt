package team.nukiya.demention.domain.support.domain

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import team.nukiya.demention.domain.support.domain.SupportStatus.SUPPORTING
import team.nukiya.demention.domain.support.domain.SupportStatus.UNSUPPORTING
import team.nukiya.demention.domain.support.exception.SupportCanNotCancelException
import java.time.LocalDateTime
import java.util.UUID

class SupportTest {
    @Test
    fun `지원 취소를 한다`() {
        // given
        val support = Support(
            userId = UUID.randomUUID(),
            helpId = UUID.randomUUID(),
            supportStatus = SUPPORTING,
            createdDateTime = LocalDateTime.now(),
        )

        // when & then
        assertDoesNotThrow {
            support.checkCancellable()
        }
    }

    @Test
    fun `전에 지원 취소를 해서 지워 취소를 할 수 없다`() {
        // given
        val support = Support(
            userId = UUID.randomUUID(),
            helpId = UUID.randomUUID(),
            supportStatus = UNSUPPORTING,
            createdDateTime = LocalDateTime.now(),
        )

        // when & then
        assertThrows<SupportCanNotCancelException> {
            support.checkCancellable()
        }
    }
}