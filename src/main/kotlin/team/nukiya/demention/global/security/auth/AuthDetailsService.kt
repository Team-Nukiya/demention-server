package team.nukiya.demention.global.security.auth

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import team.nukiya.demention.global.security.auth.AuthDetails
import team.sfe.server.domain.user.domain.repository.UserRepository
import team.sfe.server.domain.user.exception.UserNotFoundException

@Component
class AuthDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByAccountId(username) ?: throw UserNotFoundException
        return AuthDetails(user.id, user.authority)
    }
}
