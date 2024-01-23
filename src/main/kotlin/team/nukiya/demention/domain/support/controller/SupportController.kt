package team.nukiya.demention.domain.support.controller

import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.nukiya.demention.domain.support.controller.dto.SupportRequest
import team.nukiya.demention.domain.support.service.SupportService
import team.nukiya.demention.global.constant.ApiUrlConstant.SUPPORT_URL
import team.nukiya.demention.global.security.auth.AuthDetails
import java.util.UUID

@RequestMapping(SUPPORT_URL)
@RestController
class SupportController(
    private val supportService: SupportService,
) {
    @PostMapping
    fun support(
        @AuthenticationPrincipal provider: AuthDetails,
        @RequestBody @Valid request: SupportRequest,
    ) {
        supportService.support(
            request.toSupport(
                userId = UUID.fromString(provider.username),
                helpId = request.helpId,
            )
        )
    }

    @PatchMapping("/{help-id}")
    fun unsupport(
        @AuthenticationPrincipal provider: AuthDetails,
        @PathVariable("help-id") helpId: UUID,
    ) {
        supportService.unSupport(
            userId = UUID.fromString(provider.username),
            helpId = helpId,
        )
    }
}