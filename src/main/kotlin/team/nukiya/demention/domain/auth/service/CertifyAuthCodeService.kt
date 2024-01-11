package team.nukiya.demention.domain.auth.service

import org.springframework.stereotype.Service
import team.nukiya.demention.domain.auth.domain.AuthCode

@Service
class CertifyAuthCodeService(
    private val authCodeReader: AuthCodeReader,
) {

    fun certify(authCode: AuthCode) {
        authCodeReader.getAuthCodeByCode(authCode.code)
            .apply { authCode.certifyAuthCode(this) }
    }
}