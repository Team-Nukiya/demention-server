package team.nukiya.demention.domain.auth.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import team.nukiya.demention.domain.auth.domain.AuthCode
import team.nukiya.demention.domain.auth.domain.AuthCodeLimit
import team.nukiya.demention.domain.auth.domain.AuthCodeLimitMapper
import team.nukiya.demention.domain.auth.domain.AuthCodeMapper
import team.nukiya.demention.domain.auth.repisitory.AuthCodeEntityRepository
import team.nukiya.demention.domain.auth.repisitory.AuthCodeLimitEntityRepository

@Component
class AuthCodeProcessor(
    private val authCodeEntityRepository: AuthCodeEntityRepository,
    private val authCodeMapper: AuthCodeMapper,
    private val authCodeLimitEntityRepository: AuthCodeLimitEntityRepository,
    private val authCodeLimitMapper: AuthCodeLimitMapper,
    private val redisTemplate: RedisTemplate<String, String>,
) {

    fun saveAuthCode(authCode: AuthCode) =
        authCodeEntityRepository.save(
            authCodeMapper.toEntity(authCode)
        ).let { authCodeMapper.toDomain(it) }

    fun saveAuthCodeLimit(authCodeLimit: AuthCodeLimit) =
        authCodeLimitEntityRepository.save(
            authCodeLimitMapper.toEntity(authCodeLimit)
        ).let { authCodeLimitMapper.toDomain(it) }

    fun incrementLimit(phoneNumber: String) =
        redisTemplate.opsForValue()
            .increment(phoneNumber)!!.toInt()
}
