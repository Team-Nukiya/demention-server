package team.nukiya.demention.domain.user.repository

import org.springframework.data.repository.CrudRepository
import team.nukiya.demention.domain.user.domain.UserEntity
import java.util.UUID

interface UserEntityRepository : CrudRepository<UserEntity, UUID> {

    fun findByPhoneNumber(phoneNumber: String): UserEntity?

    fun existsByPhoneNumber(phoneNumber: String): Boolean
}
