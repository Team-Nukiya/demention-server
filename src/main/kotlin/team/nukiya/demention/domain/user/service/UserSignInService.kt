package team.nukiya.demention.domain.user.service

import org.springframework.stereotype.Service
import team.nukiya.demention.domain.user.controller.dto.TokenResponse
import team.nukiya.demention.domain.user.exception.UserNotFoundException
import team.nukiya.demention.infrastructure.jwt.JwtProvider

@Service
class UserSignInService(
    private val userReader: UserReader,
    private val jwtProvider: JwtProvider,
) {
    fun signIn(phoneNumber: String): TokenResponse {
        val savedUser = userReader.getByPhoneNumber(phoneNumber)
            ?: throw UserNotFoundException

        return jwtProvider.generateAllToken(
            id = savedUser.id.toString(),
            authority = savedUser.authority,
        )
    }
}
