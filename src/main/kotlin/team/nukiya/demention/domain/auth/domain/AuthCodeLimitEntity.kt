package team.nukiya.demention.domain.auth.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(timeToLive = 1800)
class AuthCodeLimitEntity(
    @Id
    val phoneNumber: String,

    val limit: Int,
)
