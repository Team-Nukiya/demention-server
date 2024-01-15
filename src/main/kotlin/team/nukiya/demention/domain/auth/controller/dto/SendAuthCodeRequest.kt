package team.nukiya.demention.domain.auth.controller.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SendAuthCodeRequest(
    @Size(min = 11, max = 11)
    @NotBlank
    val to: String,
)
