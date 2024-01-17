package team.nukiya.demention.infrastructure.sms

import team.nukiya.demention.domain.auth.domain.AuthCode

interface SmsUtil {
    fun sendCode(authCode: AuthCode)
}