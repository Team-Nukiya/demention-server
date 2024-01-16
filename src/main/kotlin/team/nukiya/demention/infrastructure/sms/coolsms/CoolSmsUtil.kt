package team.nukiya.demention.infrastructure.sms.coolsms

import net.nurigo.java_sdk.api.Message
import org.springframework.stereotype.Component
import team.nukiya.demention.domain.auth.domain.AuthCode
import team.nukiya.demention.global.exception.InternalServerErrorException
import team.nukiya.demention.infrastructure.sms.SmsUtil

@Component
class CoolSmsUtil(
    private val coolSmsProperties: CoolSmsProperties,
) : SmsUtil {

    override fun sendCode(authCode: AuthCode) {
        val message = Message(coolSmsProperties.apiKey, coolSmsProperties.secretKey)

        val params = HashMap<String, String>().apply {
            this[TO] = authCode.phoneNumber
            this[FROM] = coolSmsProperties.from
            this[TYPE] = SMS
            this[TEXT] = "${authCode.code}가 Demention 인증 코드입니다."
        }

        try {
            message.send(params)
        } catch (e: Exception) {
            throw InternalServerErrorException
        }
    }

    companion object {
        private const val TO = "to"
        private const val FROM = "from"
        private const val TYPE = "type"
        private const val SMS = "sms"
        private const val TEXT = "text"
    }
}
