package team.nukiya.demention.domain.user.service

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import team.nukiya.demention.domain.user.domain.Coordinate
import team.nukiya.demention.domain.user.domain.User
import team.nukiya.demention.domain.user.domain.UserMapper
import team.nukiya.demention.domain.user.repository.UserEntityRepository
import team.nukiya.demention.infrastructure.client.address.GetAddressService

@Transactional(readOnly = true)
@Component
class UserReader(
    private val userEntityRepository: UserEntityRepository,
    private val userMapper: UserMapper,
    private val getAddressService: GetAddressService,
) {

    fun getByPhoneNumber(phoneNumber: String) =
        userEntityRepository.findByPhoneNumber(phoneNumber)?.let {
            userMapper.toDomain(it)
        }

    fun existsByPhoneNumber(phoneNumber: String) =
        userEntityRepository.existsByPhoneNumber(phoneNumber)

    fun getAddressByCoordinate(coordinate: Coordinate) =
        getAddressService.getAddressByCoordinate(coordinate)

    fun getCurrentUser(): User {
        return SecurityContextHolder.getContext().authentication.principal as User
    }
}
