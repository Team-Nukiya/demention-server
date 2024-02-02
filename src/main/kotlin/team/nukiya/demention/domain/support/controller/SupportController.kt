package team.nukiya.demention.domain.support.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import team.nukiya.demention.domain.help.controller.dto.GetAllHelpsResponse
import team.nukiya.demention.domain.support.controller.dto.SupportRequest
import team.nukiya.demention.domain.support.service.SupportService
import team.nukiya.demention.global.constant.ApiUrlConstant.SUPPORT_URL
import team.nukiya.demention.global.dto.Paging
import team.nukiya.demention.global.dto.Paging.Companion.DEFAULT_LIMIT
import team.nukiya.demention.global.dto.Paging.Companion.DEFAULT_PAGE
import team.nukiya.demention.global.security.auth.AuthDetails
import java.util.UUID

@RequestMapping(SUPPORT_URL)
@RestController
class SupportController(
    private val supportService: SupportService,
) {
    @ResponseStatus(HttpStatus.CREATED)
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

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{help-id}")
    fun cancelSupport(
        @AuthenticationPrincipal provider: AuthDetails,
        @PathVariable("help-id") helpId: UUID,
    ) {
        supportService.cancel(
            userId = UUID.fromString(provider.username),
            helpId = helpId,
        )
    }

    @GetMapping("/histories")
    fun getHistories(
        @AuthenticationPrincipal provider: AuthDetails,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) size: Long?,
    ): GetAllHelpsResponse {
        val histories = supportService.getHistories(provider.user, Paging(page = page, size = size))
        return GetAllHelpsResponse(histories)
    }
}