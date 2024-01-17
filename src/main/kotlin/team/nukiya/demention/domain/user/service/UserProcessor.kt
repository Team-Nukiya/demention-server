package team.nukiya.demention.domain.user.service

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import team.nukiya.demention.domain.user.domain.User
import team.nukiya.demention.domain.user.domain.UserMapper
import team.nukiya.demention.domain.user.repository.UserEntityRepository

@Transactional
@Component
class UserProcessor(
    private val userEntityRepository: UserEntityRepository,
    private val userMapper: UserMapper,
) {
    fun saveUser(user: User): User =
        userEntityRepository.save(
            userMapper.toEntity(user)
        ).let { userMapper.toDomain(it) }
}
