package team.nukiya.demention.domain.auth.service

import org.springframework.stereotype.Service
import team.nukiya.demention.domain.auth.domain.AuthCode
import team.nukiya.demention.infrastructure.sms.SmsUtil
import kotlin.random.Random

@Service
class SendAuthCodeService(
    private val authCodeProcessor: AuthCodeProcessor,
    private val smsUtil: SmsUtil,
) {

    fun send(to: String) {
        val code = Random.generateRandomCode()

        val authCode = AuthCode(
            code = code,
            phoneNumber = to
        )

        val savedAuthCode = authCodeProcessor.saveAuthCode(authCode)

        smsUtil.sendCode(
            code = savedAuthCode.code,
            to = savedAuthCode.phoneNumber
        )
    }
}

fun Random.generateRandomCode() =
    StringBuffer().apply {
        repeat(6) {
            this.append(Random.nextInt(9))
        }
    }.toString()
