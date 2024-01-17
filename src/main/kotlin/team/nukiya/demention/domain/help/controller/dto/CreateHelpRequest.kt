package team.nukiya.demention.domain.help.controller.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import team.nukiya.demention.domain.help.domain.Help
import java.time.LocalDateTime
import java.util.UUID

data class CreateHelpRequest(
    @NotBlank
    @Size(max = 15)
    val title: String,

    @NotBlank
    @Size(max = 300)
    val content: String,

    @NotBlank
    @Size(max = 15)
    val compensation: String,

    val helpImageUrl: String?,

    @NotNull
    val helpStartDateTime: LocalDateTime,

    @NotNull
    val helpEndDateTime: LocalDateTime,
) {
    fun toHelp(userId: UUID) =
        Help(
            userId = userId,
            title = this.title,
            content = this.content,
            compensation = this.compensation,
            helpImageUrl = this.helpImageUrl ?: "Default Image",
            helpStartDateTime = this.helpStartDateTime,
            helpEndDateTime = this.helpEndDateTime,
        )
}
