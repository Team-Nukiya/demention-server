package team.nukiya.demention.domain.help.controller

import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.nukiya.demention.domain.help.controller.dto.CreateHelpRequest
import team.nukiya.demention.domain.help.controller.dto.GetAllHelpsResponse
import team.nukiya.demention.domain.help.controller.dto.GetHelpDetailsResponse
import team.nukiya.demention.domain.help.controller.dto.UpdateHelpRequest
import team.nukiya.demention.domain.help.service.HelpService
import team.nukiya.demention.global.security.auth.AuthDetails
import java.util.UUID

@RequestMapping("/v1/helps")
@RestController
class HelpController(
    private val helpService: HelpService,
) {
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
    fun getHelpDetails(@PathVariable(HELP_ID) helpId: UUID): GetHelpDetailsResponse {
        val helpDetails = helpService.getDetails(helpId = helpId)
        return GetHelpDetailsResponse(helpDetails = helpDetails)
    }


    @GetMapping
    fun getAllHelps(@RequestParam(defaultValue = "0") page: Long): GetAllHelpsResponse {
        val allHelps = helpService.getAll(page = page)
        return GetAllHelpsResponse(allHelps = allHelps)
    }

    companion object {
        private const val HELP_ID = "help-id"
    }
}
