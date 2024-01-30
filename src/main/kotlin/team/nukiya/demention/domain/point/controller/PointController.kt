package team.nukiya.demention.domain.point.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import team.nukiya.demention.domain.point.controller.dto.GivePointRequest
import team.nukiya.demention.domain.point.service.PointService
import team.nukiya.demention.global.constant.ApiUrlConstant.POINT_URL
import team.nukiya.demention.global.security.auth.AuthDetails
import java.util.UUID

@RequestMapping(POINT_URL)
@RestController
class PointController(
    private val pointService: PointService,
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun givePoint(
        @RequestBody @Valid request: GivePointRequest,
        @AuthenticationPrincipal provider: AuthDetails,
    ) {
        pointService.give(
            request.toPoint(UUID.fromString(provider.username))
        )
    }
}