package team.nukiya.demention.domain.auth.controller

import jakarta.validation.constraints.NotBlank
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.nukiya.demention.domain.auth.domain.AuthCode
import team.nukiya.demention.domain.auth.service.CertifyAuthCodeService

@Validated
@RequestMapping("/v1/auth")
@RestController
class AuthController(
    private val certifyAuthCodeService: CertifyAuthCodeService,
) {

    @GetMapping("/certified")
    fun certifyAuthCode(
        @RequestParam("code") @NotBlank code: String,
        @RequestParam("phone-number") @NotBlank phoneNumber: String,
    ) {
        certifyAuthCodeService.certify(
            AuthCode(
                code = code,
                phoneNumber = phoneNumber,
            )
        )
    }
}
