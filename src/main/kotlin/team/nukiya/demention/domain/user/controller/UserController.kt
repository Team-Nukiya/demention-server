package team.nukiya.demention.domain.user.controller

import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.nukiya.demention.domain.user.controller.dto.TokenResponse
import team.nukiya.demention.domain.user.controller.dto.UserSignInRequest
import team.nukiya.demention.domain.user.controller.dto.UserSignUpRequest
import team.nukiya.demention.domain.user.domain.UserInformation
import team.nukiya.demention.domain.user.service.GetMyInformationService
import team.nukiya.demention.domain.user.service.UserSignInService
import team.nukiya.demention.domain.user.service.UserSignUpService
import team.nukiya.demention.global.constant.ApiUrlConstant.USER_URL
import team.nukiya.demention.global.security.auth.AuthDetails

@RequestMapping(USER_URL)
@RestController
class UserController(
    private val userSignUpService: UserSignUpService,
    private val userSignInService: UserSignInService,
    private val getMyInformationService: GetMyInformationService,
) {
    @PostMapping("/sign-up")
    fun signUp(@RequestBody @Valid request: UserSignUpRequest): TokenResponse =
        userSignUpService.signUp(
            phoneNumber = request.phoneNumber,
            coordinate = request.toCoordinate(),
        )

    @PostMapping("/sign-in")
    fun signIn(@RequestBody @Valid request: UserSignInRequest): TokenResponse =
        userSignInService.signIn(request.phoneNumber)

    @GetMapping("/my")
    fun getMyInformation(@AuthenticationPrincipal provider: AuthDetails): UserInformation =
        getMyInformationService.get(provider.user)
}
