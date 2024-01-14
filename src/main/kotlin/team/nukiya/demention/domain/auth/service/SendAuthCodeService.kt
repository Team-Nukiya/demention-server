package team.nukiya.demention.domain.auth.service

import org.springframework.stereotype.Service
import team.nukiya.demention.domain.auth.domain.AuthCode
import team.nukiya.demention.infrastructure.sms.SmsUtil

@Service
class SendAuthCodeService(
    private val authCodeProcessor: AuthCodeProcessor,
    private val smsUtil: SmsUtil,
) {

    fun send(to: String) {
        val randomCode = AuthCode.generateRandomCode()

        val authCode = AuthCode(
            code = randomCode,
            phoneNumber = to
        )

        val savedAuthCode = authCodeProcessor.saveAuthCode(authCode)

        smsUtil.sendCode(savedAuthCode)
    }
}
