package team.nukiya.demention.infrastructure.sms

interface SmsUtil {

    fun sendCode(code: String, to: String)
}