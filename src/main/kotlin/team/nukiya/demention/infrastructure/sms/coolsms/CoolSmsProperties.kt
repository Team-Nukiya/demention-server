package team.nukiya.demention.infrastructure.sms.coolsms

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "cool-sms")
data class CoolSmsProperties(
    val apiKey: String,
    val secretKey: String,
    val from: String,
)