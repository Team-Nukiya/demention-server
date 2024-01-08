package team.nukiya.demention.domain.auth.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash(timeToLive = 300)
class AuthCodeEntity(
    @Id
    val code: String,

    @Indexed
    val phoneNumber: String,
)
