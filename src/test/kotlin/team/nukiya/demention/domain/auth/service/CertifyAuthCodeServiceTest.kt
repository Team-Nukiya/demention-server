package team.nukiya.demention.domain.auth.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.anyString
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import team.nukiya.demention.domain.auth.domain.AuthCode

@ExtendWith(MockitoExtension::class)
class CertifyAuthCodeServiceTest {

    @InjectMocks
    private lateinit var certifyAuthCodeService: CertifyAuthCodeService

    @Mock
    private lateinit var authCOdeReader: AuthCodeReader

    @Test
    fun `유저가 보낸 인증 코드와 전화번호가 올바른지 확인한다`() {
        // given
        val code = "111111"
        val phoneNumber = "010xxxxxxxx"

        val authCode = AuthCode(
            code = code,
            phoneNumber = phoneNumber,
        )

        given(authCOdeReader.getAuthCodeByCode(anyString()))
            .willReturn(authCode)

        // when & then
        assertDoesNotThrow {
            certifyAuthCodeService.certify(authCode)
        }
    }
}