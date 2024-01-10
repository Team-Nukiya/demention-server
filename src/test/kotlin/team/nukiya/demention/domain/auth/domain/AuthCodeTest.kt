package team.nukiya.demention.domain.auth.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import team.nukiya.demention.domain.auth.exception.WrongAuthCodeException

class AuthCodeTest {

    @Test
    internal fun `유저가 보낸 인증 코드와 유저가 발급 받은 코드가 같다`() {
        // given
        val requestAuthCode = AuthCode(
            code = "111111",
            phoneNumber = "010xxxxxxxx"
        )

        val savedAuthCode = AuthCode(
            code = "111111",
            phoneNumber = "010xxxxxxxx"
        )

        // when & then
        assertDoesNotThrow {
            requestAuthCode.certifyAuthCode(savedAuthCode)
        }
    }

    @Test
    internal fun `유저가 보낸 인증 코드와 유저가 발급 받은 코드가 다르다`() {
        val requestAuthCode = AuthCode(
            code = "111111",
            phoneNumber = "010xxxxxxxx"
        )

        val savedAuthCode = AuthCode(
            code = "999999",
            phoneNumber = "010xxxxxxxx"
        )

        // when & then
        assertThrows<WrongAuthCodeException> {
            requestAuthCode.certifyAuthCode(savedAuthCode)
        }
    }

    @Test
    internal fun `유저가 보낸 인증 코드와 유저가 발급 받은 코드는 같지만 전화번호 정보가 다르다`() {
        val requestAuthCode = AuthCode(
            code = "111111",
            phoneNumber = "010xxxxxxxx"
        )

        val savedAuthCode = AuthCode(
            code = "111111",
            phoneNumber = "010oooooooo"
        )

        // when & then
        assertThrows<WrongAuthCodeException> {
            requestAuthCode.certifyAuthCode(savedAuthCode)
        }
    }
}