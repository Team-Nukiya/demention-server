package team.nukiya.demention.domain.user.controller.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import team.nukiya.demention.domain.user.domain.Coordinate

data class UserSignUpRequest(
    @Size(min = 11, max = 11)
    @NotBlank
    val phoneNumber: String,

    @NotBlank
    val latitude: String,

    @NotBlank
    val longitude: String,
) {
    fun toCoordinate() =
        Coordinate(
            latitude = this.latitude,
            longitude = this.longitude,
        )
}
