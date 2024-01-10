package team.nukiya.demention.domain.auth.controller.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SendAuthCodeRequest(

    @Size(min = 6, max = 6)
    @NotBlank
    val to: String,
)
