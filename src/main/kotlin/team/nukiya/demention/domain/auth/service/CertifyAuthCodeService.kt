package team.nukiya.demention.domain.auth.service

import org.springframework.stereotype.Service
import team.nukiya.demention.domain.auth.domain.AuthCode
import team.nukiya.demention.domain.auth.exception.AuthCodeLimitNotFoundException
import team.nukiya.demention.domain.auth.exception.AuthCodeNotFoundException

@Service
class CertifyAuthCodeService(
    private val authCodeReader: AuthCodeReader,
) {
    fun certify(authCode: AuthCode) {
        authCodeReader.getAuthCodeLimitByPhoneNumber(authCode.phoneNumber)
            ?.apply { checkOverLimit() }
            ?: throw AuthCodeLimitNotFoundException

        authCodeReader.getAuthCodeByCode(authCode.code)
            ?.apply { certifyAuthCode(authCode) }
            ?: throw AuthCodeNotFoundException
    }
}
