package team.nukiya.demention.global.security.auth

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import team.nukiya.demention.domain.user.domain.UserMapper
import team.nukiya.demention.domain.user.exception.UserNotFoundException
import team.nukiya.demention.domain.user.repository.UserRepository
import java.util.UUID

@Component
class AuthDetailsService(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val userId = UUID.fromString(username)
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotFoundException
        return AuthDetails(userMapper.toDomain(user)!!)
    }
}
