package team.nukiya.demention.domain.auth.repisitory

import org.springframework.data.repository.CrudRepository
import team.nukiya.demention.domain.auth.domain.RefreshTokenEntity

interface RefreshTokenEntityRepository : CrudRepository<RefreshTokenEntity, String> {
    fun findByToken(token: String): RefreshTokenEntity?
}