package team.nukiya.demention.domain.auth.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed

@RedisHash(timeToLive = 600)
class AuthCodeEntity(
    @Id
    val code: String,

    @Indexed
    val phoneNumber: String,
)
