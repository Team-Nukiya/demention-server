package team.nukiya.demention.domain.auth.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.doNothing
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import team.nukiya.demention.domain.auth.domain.AuthCode
import team.nukiya.demention.infrastructure.sms.SmsUtil

@ExtendWith(MockitoExtension::class)
class SendAuthCodeServiceTest {

    @InjectMocks
    private lateinit var sendAuthCodeService: SendAuthCodeService

    @Mock
    private lateinit var authCodeProcessor: AuthCodeProcessor

    @Mock
    private lateinit var smsUtil: SmsUtil

    @Test
    fun `테스트`() {
        // given
        val phoneNumber = "010xxxxxxxx"
        val authCode = AuthCode(
            code = "111111",
            phoneNumber = phoneNumber,
        )

        given(authCodeProcessor.saveAuthCode(any()))
            .willReturn(authCode)

        doNothing().`when`(smsUtil).sendCode(any())

        // when
        sendAuthCodeService.send(phoneNumber)

        // then
        verify(authCodeProcessor).saveAuthCode(any())
        verify(smsUtil).sendCode(any())
    }
}