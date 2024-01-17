package team.nukiya.demention.infrastructure.client.address

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "kakaoAddressFeignClient", url = "https://dapi.kakao.com")
interface KakaoAddressFeignClient {
    @GetMapping("/v2/local/geo/coord2address.json")
    fun getAddressByCoordinate(
        @RequestHeader("Authorization") apiKey: String,
        @RequestParam x: String,
        @RequestParam y: String,
    ) : AddressResponse
}