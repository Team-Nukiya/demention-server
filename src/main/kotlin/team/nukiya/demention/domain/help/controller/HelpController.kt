package team.nukiya.demention.domain.help.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import team.nukiya.demention.domain.help.controller.dto.CreateHelpRequest
import team.nukiya.demention.domain.help.controller.dto.GetAllHelpsResponse
import team.nukiya.demention.domain.help.controller.dto.UpdateHelpRequest
import team.nukiya.demention.domain.help.domain.HelpDetails
import team.nukiya.demention.domain.help.domain.HelpStatus
import team.nukiya.demention.domain.help.service.HelpService
import team.nukiya.demention.global.constant.ApiUrlConstant.HELP_URL
import team.nukiya.demention.global.dto.Paging
import team.nukiya.demention.global.security.auth.AuthDetails
import java.util.UUID

@Validated
@RequestMapping(HELP_URL)
@RestController
class HelpController(
    private val helpService: HelpService,
) {
    @ResponseStatus(CREATED)
    @PostMapping
    fun createHelp(
        @RequestBody @Valid request: CreateHelpRequest,
        @AuthenticationPrincipal provider: AuthDetails,
    ) {
        helpService.create(
            help = request.toHelp(
                userId = UUID.fromString(provider.username)
            ),
        )
    }

    @ResponseStatus(NO_CONTENT)
    @PatchMapping("/{$HELP_ID}")
    fun modifyHelp(
        @RequestBody @Valid request: UpdateHelpRequest,
        @PathVariable(HELP_ID) helpId: UUID,
        @AuthenticationPrincipal provider: AuthDetails,
    ) {
        val userId = UUID.fromString(provider.username)
        helpService.modify(
            help = request.toHelp(
                userId = userId
            ),
            helpId = helpId,
            userId = userId,
        )
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{$HELP_ID}")
    fun removeHelp(
        @PathVariable(HELP_ID) helpId: UUID,
        @AuthenticationPrincipal provider: AuthDetails,
    ) {
        helpService.remove(
            helpId = helpId,
            userId = UUID.fromString(provider.username)
        )
    }

    @GetMapping("/{${HELP_ID}}")
    fun getHelpDetails(@PathVariable(HELP_ID) helpId: UUID): HelpDetails =
        helpService.getDetails(helpId = helpId)

    @GetMapping
    fun getAllHelps(
        @NotNull @RequestParam("help-status") helpStatus: HelpStatus,
        @AuthenticationPrincipal provider: AuthDetails,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) size: Long?,
    ): GetAllHelpsResponse {
        val helps = helpService.getAll(
            helpStatus = helpStatus,
            currentUser = provider.user,
            paging = Paging(page = page, size = size)
        )
        return GetAllHelpsResponse(helps)
    }

    @GetMapping("/histories")
    fun getHistories(
        @AuthenticationPrincipal provider: AuthDetails,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) size: Long?,
    ): GetAllHelpsResponse {
        val histories = helpService.getHistories(provider.user, Paging(page = page, size = size))
        return GetAllHelpsResponse(histories)
    }

    companion object {
        private const val HELP_ID = "help-id"
    }
}
