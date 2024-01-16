package team.nukiya.demention.domain.auth.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import team.nukiya.demention.domain.auth.domain.AuthCodeLimitMapper
import team.nukiya.demention.domain.auth.domain.AuthCodeMapper
import team.nukiya.demention.domain.auth.repisitory.AuthCodeEntityRepository
import team.nukiya.demention.domain.auth.repisitory.AuthCodeLimitEntityRepository

@Component
class AuthCodeReader(
    private val authCodeEntityRepository: AuthCodeEntityRepository,
    private val authCodeMapper: AuthCodeMapper,
    private val authCodeLimitEntityRepository: AuthCodeLimitEntityRepository,
    private val authCodeLimitMapper: AuthCodeLimitMapper,
) {

    fun getAuthCodeByCode(code: String) =
        authCodeEntityRepository.findByIdOrNull(code)?.let {
            authCodeMapper.toDomain(it)
        }

    fun getAuthCodeLimitByPhoneNumber(phoneNumber: String) =
        authCodeLimitEntityRepository.findByIdOrNull(phoneNumber)?.let {
            authCodeLimitMapper.toDomain(it)
        }
}
