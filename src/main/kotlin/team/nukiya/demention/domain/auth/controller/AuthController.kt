package team.nukiya.demention.domain.auth.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.nukiya.demention.domain.auth.controller.dto.SendAuthCodeRequest
import team.nukiya.demention.domain.auth.controller.dto.SendAuthCodeResponse
import team.nukiya.demention.domain.auth.domain.AuthCode
import team.nukiya.demention.domain.auth.service.CertifyAuthCodeService
import team.nukiya.demention.domain.auth.service.SendAuthCodeService
import team.nukiya.demention.global.constant.ApiUrlConstant.AUTH_URL

@Validated
@RequestMapping(AUTH_URL)
@RestController
class AuthController(
    private val sendAuthCodeService: SendAuthCodeService,
    private val certifyAuthCodeService: CertifyAuthCodeService,
) {
    @PostMapping("/codes")
    fun sendCode(@RequestBody @Valid request: SendAuthCodeRequest): SendAuthCodeResponse {
        val (limit, code) = sendAuthCodeService.send(request.to)
        return SendAuthCodeResponse(limit = limit, code = code)
    }

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
