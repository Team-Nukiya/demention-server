package team.nukiya.demention.domain.auth.repisitory

import org.springframework.data.repository.CrudRepository
import team.nukiya.demention.domain.auth.domain.AuthCodeLimitEntity

interface AuthCodeLimitEntityRepository : CrudRepository<AuthCodeLimitEntity, String> {

    fun findByPhoneNumber(phoneNumber: String): AuthCodeLimitEntity?
}
