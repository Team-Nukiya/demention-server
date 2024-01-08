package team.nukiya.demention.domain.auth.repisitory

import org.springframework.data.repository.CrudRepository
import team.nukiya.demention.domain.auth.domain.AuthCodeEntity

interface AuthCodeRepository : CrudRepository<AuthCodeEntity, String>
