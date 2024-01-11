package team.nukiya.demention.domain.auth.service

import org.springframework.data.repository.findByIdOrNull
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

    fun getAuthCodeByCode(code: String): AuthCode {
        return authCodeEntityRepository.findByIdOrNull(code)?.let {
            authCodeMapper.toDomain(it)
        } ?: throw AuthCodeNotFoundException
    }
}
