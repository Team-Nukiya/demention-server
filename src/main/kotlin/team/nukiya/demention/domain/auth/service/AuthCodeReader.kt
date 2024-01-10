package team.nukiya.demention.domain.auth.service

import org.springframework.stereotype.Component
import team.nukiya.demention.domain.auth.domain.AuthCode
import team.nukiya.demention.domain.auth.domain.AuthCodeMapper
import team.nukiya.demention.domain.auth.exception.AuthCodeNotFoundException
import team.nukiya.demention.domain.auth.repisitory.AuthCodeEntityRepository

@Component
class AuthCodeReader(
    private val authCodeEntityRepository: AuthCodeEntityRepository,
    private val authCodeMapper: AuthCodeMapper,
) {

    fun getAuthCodeByCodeAndPhoneNumber(authCode: AuthCode): AuthCode {
        val authCodeEntity = authCodeEntityRepository.findByCodeAndPhoneNumber(authCode.code, authCode.phoneNumber)
            ?: throw AuthCodeNotFoundException

        return authCodeMapper.toDomain(authCodeEntity)
    }
}
