package team.nukiya.demention.domain.auth.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.anyString
import org.mockito.BDDMockito.doNothing
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import team.nukiya.demention.domain.auth.domain.AuthCode
import team.nukiya.demention.domain.auth.domain.AuthCodeLimit
import team.nukiya.demention.domain.auth.domain.AuthCodeLimit.Companion.LIMIT
import team.nukiya.demention.infrastructure.sms.SmsUtil

@ExtendWith(MockitoExtension::class)
class SendAuthCodeServiceTest {

    @InjectMocks
    private lateinit var sendAuthCodeService: SendAuthCodeService

    @Mock
    private lateinit var authCodeProcessor: AuthCodeProcessor

//    @Mock
//    private lateinit var smsUtil: SmsUtil

    @Test
    fun `유저가 보낸 전화번호로 인증 코드를 보낸다`() {
        // given
        val phoneNumber = "010xxxxxxxx"
        val authCode = AuthCode(
            code = "111111",
            phoneNumber = phoneNumber,
        )

        var limit = 0
        given(authCodeProcessor.incrementLimit(anyString()))
            .willReturn(limit++)

        val authCodeLimit = AuthCodeLimit(
            phoneNumber = phoneNumber,
            limit = limit,
        )

        given(authCodeProcessor.saveAuthCodeLimit(any()))
            .willReturn(authCodeLimit)

        given(authCodeProcessor.saveAuthCode(any()))
            .willReturn(authCode)

//        doNothing().`when`(smsUtil).sendCode(any(AuthCode::class.java))

        // when
        val response = sendAuthCodeService.send(phoneNumber)

        // then
        assertThat(response.first).isLessThanOrEqualTo(LIMIT)
        assertThat(response.second).isNotBlank().hasSize(6)

//        verify(authCodeProcessor).saveAuthCode(any())
//        verify(smsUtil).sendCode(any(AuthCode::class.java))
    }
}