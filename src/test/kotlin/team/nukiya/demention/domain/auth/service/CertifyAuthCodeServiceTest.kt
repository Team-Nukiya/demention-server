package team.nukiya.demention.domain.auth.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.anyString
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import team.nukiya.demention.domain.auth.domain.AuthCode
import team.nukiya.demention.domain.auth.domain.AuthCodeLimit
import team.nukiya.demention.domain.auth.domain.AuthCodeLimit.Companion.LIMIT
import team.nukiya.demention.domain.auth.exception.AuthCodeLimitNotFoundException
import team.nukiya.demention.domain.auth.exception.AuthCodeNotFoundException
import team.nukiya.demention.domain.auth.exception.AuthCodeOverLimitException
import team.nukiya.demention.domain.auth.exception.WrongAuthCodeException

@ExtendWith(MockitoExtension::class)
class CertifyAuthCodeServiceTest {
    @InjectMocks
    private lateinit var certifyAuthCodeService: CertifyAuthCodeService

    @Mock
    private lateinit var authCodeReader: AuthCodeReader

    @Test
    fun `유저가 보낸 인증 코드와 전화번호가 올바른지 확인한다`() {
        // given
        val code = "111111"
        val phoneNumber = "010xxxxxxxx"

        val authCodeLimit = AuthCodeLimit(
            phoneNumber = phoneNumber,
            limit = 1,
        )

        val authCode = AuthCode(
            code = code,
            phoneNumber = phoneNumber,
        )

        given(authCodeReader.getAuthCodeLimitByPhoneNumber(anyString()))
            .willReturn(authCodeLimit)

        given(authCodeReader.getAuthCodeByCode(anyString()))
            .willReturn(authCode)

        // when & then
        assertDoesNotThrow {
            certifyAuthCodeService.certify(authCode)
        }
    }

    @Test
    fun `인증 코드 요청을 인증 제한 횟수보다 많이 해서 인증할 수 없다`() {
        // given
        val phoneNumber = "010xxxxxxxx"
        val authCodeLimit = AuthCodeLimit(
            phoneNumber = phoneNumber,
            limit = LIMIT + 1,
        )

        val authCode = AuthCode(
            code = "111111",
            phoneNumber = phoneNumber
        )

        given(authCodeReader.getAuthCodeLimitByPhoneNumber(anyString()))
            .willReturn(authCodeLimit)

        // when & then
        assertThrows<AuthCodeOverLimitException> {
            certifyAuthCodeService.certify(authCode)
        }
    }

    @Test
    fun `존재하지 않는 전화번호로 인증 코드 제한 객체를 가져오지 못해 예외가 발생한다`() {
        // given
        val authCode = AuthCode(
            code = "111111",
            phoneNumber = "010xxxxxxxx"
        )

        given(authCodeReader.getAuthCodeLimitByPhoneNumber(anyString()))
            .willReturn(null)

        // when & then
        assertThrows<AuthCodeLimitNotFoundException> {
            certifyAuthCodeService.certify(authCode)
        }
    }

    @Test
    fun `유저가 보낸 인증 코드와 발급한 인증 코드가 다르다`() {
        // given
        val phoneNumber = "010xxxxxxxx"
        val authCodeLimit = AuthCodeLimit(
            phoneNumber = phoneNumber,
            limit = 1,
        )

        val authCode = AuthCode(
            code = "111111",
            phoneNumber = phoneNumber
        )

        val wrongAuthCode = AuthCode(
            code = "999999",
            phoneNumber = "010oooooooo",
        )

        given(authCodeReader.getAuthCodeLimitByPhoneNumber(anyString()))
            .willReturn(authCodeLimit)

        given(authCodeReader.getAuthCodeByCode(anyString()))
            .willReturn(authCode)

        // when & then
        assertThrows<WrongAuthCodeException> {
            certifyAuthCodeService.certify(wrongAuthCode)
        }
    }

    @Test
    fun `존재하지 않는 인증 코드로 인증 코드 객체를 가져오지 못해 예외가 발생한다`() {
        // given
        val phoneNumber = "010xxxxxxxx"
        val authCodeLimit = AuthCodeLimit(
            phoneNumber = phoneNumber,
            limit = 1,
        )

        val authCode = AuthCode(
            code = "111111",
            phoneNumber = phoneNumber
        )

        given(authCodeReader.getAuthCodeLimitByPhoneNumber(anyString()))
            .willReturn(authCodeLimit)

        given(authCodeReader.getAuthCodeByCode(anyString()))
            .willReturn(null)

        // when & then
        assertThrows<AuthCodeNotFoundException> {
            certifyAuthCodeService.certify(authCode)
        }
    }
}
