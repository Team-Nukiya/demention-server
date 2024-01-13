package team.nukiya.demention.domain.user.service

import org.springframework.stereotype.Service
import team.nukiya.demention.domain.user.controller.dto.TokenResponse
import team.nukiya.demention.domain.user.domain.Coordinate
import team.nukiya.demention.domain.user.domain.User
import team.nukiya.demention.domain.user.exception.UserAlreadyExistsException
import team.nukiya.demention.infrastructure.jwt.JwtProvider

@Service
class UserSignUpService(
    private val userReader: UserReader,
    private val userProcessor: UserProcessor,
    private val jwtProvider: JwtProvider,
) {

    fun signUp(
        phoneNumber: String,
        coordinate: Coordinate,
    ): TokenResponse {
        if (userReader.existsByPhoneNumber(phoneNumber)) {
            throw UserAlreadyExistsException
        }

        val address = userReader.getAddressByCoordinate(coordinate)

        val savedUser = userProcessor.saveUser(
            User(
                phoneNumber = phoneNumber,
                nickName = User.generateRandomNickName(),
                address = address,
            )
        )

        return jwtProvider.generateAllToken(
            id = savedUser.id.toString(),
            authority = savedUser.authority
        )
    }
}