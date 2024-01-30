package team.nukiya.demention.domain.user.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.nukiya.demention.domain.user.domain.User
import team.nukiya.demention.domain.user.domain.UserInformation
import team.nukiya.demention.domain.user.exception.UserNotFoundException

@Transactional(readOnly = true)
@Service
class GetMyInformationService(
    private val userReader: UserReader,
) {
    fun get(user: User): UserInformation =
        userReader.getInformation(user) ?: throw UserNotFoundException
}