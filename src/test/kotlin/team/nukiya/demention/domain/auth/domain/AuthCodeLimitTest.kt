package team.nukiya.demention.domain.auth.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import team.nukiya.demention.domain.auth.exception.AuthCodeOverLimitException

class AuthCodeLimitTest {

    @Test
    fun `인증 코드 요청 횟수가 5번 이하다`() {
        // given
        val authCodeLimit = AuthCodeLimit(
            phoneNumber = "010xxxxxxxx",
            limit = 5,
        )

        // when & then
        assertDoesNotThrow {
            authCodeLimit.checkOverLimit()
        }
    }

    @Test
    fun `인증 코드 요청 횟수가 5번을 초과한다`() {
        // given
        val authCodeLimit = AuthCodeLimit(
            phoneNumber = "010xxxxxxxx",
            limit = 6,
        )

        // when & then
        assertThrows<AuthCodeOverLimitException> {
            authCodeLimit.checkOverLimit()
        }
    }
}
