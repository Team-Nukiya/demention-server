package team.nukiya.demention.domain.user.service

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import team.nukiya.demention.domain.user.domain.Address
import team.nukiya.demention.domain.user.domain.Coordinate
import team.nukiya.demention.domain.user.domain.UserMapper
import team.nukiya.demention.domain.user.exception.UserNotFoundException
import team.nukiya.demention.domain.user.repository.UserEntityRepository
import team.nukiya.demention.infrastructure.client.KakaoAddressFeignClient

@Transactional(readOnly = true)
@Component
class UserReader(
    private val userEntityRepository: UserEntityRepository,
    private val userMapper: UserMapper,
    private val kakaoAddressFeignClient: KakaoAddressFeignClient,
) {

    fun getByPhoneNumber(phoneNumber: String) =
        userEntityRepository.findByPhoneNumber(phoneNumber)?.let {
            userMapper.toDomain(it)
        } ?: throw UserNotFoundException


    fun existsByPhoneNumber(phoneNumber: String) =
        userEntityRepository.existsByPhoneNumber(phoneNumber)

    fun getAddressByCoordinate(coordinate: Coordinate): Address {
        val addressResponse = kakaoAddressFeignClient.getAddressByCoordinate(
            x = coordinate.longitude,
            y = coordinate.latitude,
        )

        val addressElement = addressResponse.documents[0].address
        return Address(
            addressName = addressElement.addressName,
            sido = addressElement.sido,
            gungu = addressElement.gungu,
            eupMyeonDong = addressElement.eupMyeonDong,
        )
    }
}
