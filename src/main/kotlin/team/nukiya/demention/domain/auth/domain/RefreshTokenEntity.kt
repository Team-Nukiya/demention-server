package team.nukiya.demention.domain.auth.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 14)
class RefreshTokenEntity(
    @Id
    val accountId: String,

    @Indexed
    var token: String
) {
    fun updateToken(token: String) {
        this.token = token
    }
}