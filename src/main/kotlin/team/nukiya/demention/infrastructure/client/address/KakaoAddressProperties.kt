package team.nukiya.demention.infrastructure.client.address

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "kakao-address")
data class KakaoAddressProperties(
    val apiKey: String,
)
