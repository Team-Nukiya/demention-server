package team.nukiya.demention.domain.auth.service

import org.springframework.stereotype.Service
import team.nukiya.demention.domain.auth.domain.AuthCode
import team.nukiya.demention.domain.auth.domain.AuthCodeLimit

@Service
class SendAuthCodeService(
    private val authCodeProcessor: AuthCodeProcessor,
    // TODO: private val smsUtil: SmsUtil,
) {

    fun send(phoneNumber: String): Pair<Int, String> {
        val limit = workAuthCodeLimit(phoneNumber = phoneNumber)
        val code = workAuthCode(phoneNumber = phoneNumber)

        return Pair(limit, code)
    }

    private fun workAuthCodeLimit(phoneNumber: String): Int{
        val incrementLimit = authCodeProcessor.incrementLimit(phoneNumber = phoneNumber)

        val authCodeLimit = AuthCodeLimit(
            phoneNumber = phoneNumber,
            limit = incrementLimit,
        ).apply { checkOverLimit() }

        val savedAuthCodeLimit = authCodeProcessor.saveAuthCodeLimit(authCodeLimit)

        return savedAuthCodeLimit.limit
    }

    private fun workAuthCode(phoneNumber: String): String {
        val randomCode = AuthCode.generateRandomCode()

        val authCode = AuthCode(
            code = randomCode,
            phoneNumber = phoneNumber
        )

        val savedAuthCode = authCodeProcessor.saveAuthCode(authCode)

        return savedAuthCode.code
    }
}
