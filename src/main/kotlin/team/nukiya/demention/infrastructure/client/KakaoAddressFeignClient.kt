package team.nukiya.demention.infrastructure.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import team.nukiya.demention.infrastructure.client.KakaoAddressProperties.API_KEY

@FeignClient(name = "kakaoAddressFeignClient", url = "https://dapi.kakao.com")
interface KakaoAddressFeignClient {

    @GetMapping("/v2/local/geo/coord2address.json")
    fun getAddressByCoordinate(
        @RequestHeader("Authorization") authorization: String = API_KEY,
        @RequestParam x: String,
        @RequestParam y: String,
    ) :AddressResponse
}