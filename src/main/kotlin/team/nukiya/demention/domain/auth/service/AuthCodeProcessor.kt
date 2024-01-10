package team.nukiya.demention.domain.auth.service

import org.springframework.stereotype.Component
import team.nukiya.demention.domain.auth.domain.AuthCode
import team.nukiya.demention.domain.auth.domain.AuthCodeEntity
import team.nukiya.demention.domain.auth.domain.AuthCodeMapper
import team.nukiya.demention.domain.auth.repisitory.AuthCodeEntityRepository

@Component
class AuthCodeProcessor(
    private val authCodeEntityRepository: AuthCodeEntityRepository,
    private val authCodeMapper: AuthCodeMapper,
) {

    fun saveAuthCode(authCode: AuthCode) =
        authCodeEntityRepository.save(
            AuthCodeEntity(
                code = authCode.code,
                phoneNumber = authCode.phoneNumber,
            )
        ).apply { authCodeMapper.toDomain(this) }
}