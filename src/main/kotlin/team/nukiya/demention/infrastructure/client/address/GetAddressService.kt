package team.nukiya.demention.infrastructure.client.address

import org.springframework.stereotype.Component
import team.nukiya.demention.domain.user.domain.Address
import team.nukiya.demention.domain.user.domain.Coordinate

@Component
class GetAddressService(
    private val kakaoAddressProperties: KakaoAddressProperties,
    private val kakaoAddressFeignClient: KakaoAddressFeignClient,
) {
    fun getAddressByCoordinate(coordinate: Coordinate): Address {
        val addressResponse = kakaoAddressFeignClient.getAddressByCoordinate(
            apiKey = kakaoAddressProperties.apiKey,
            x = coordinate.longitude,
            y = coordinate.latitude,
        )

        return addressResponse.documents[0].address.let {
            Address(
                addressName = it.addressName,
                sido = it.sido,
                gungu = it.gungu,
                eupMyeonDong = it.eupMyeonDong,
            )
        }
    }
}