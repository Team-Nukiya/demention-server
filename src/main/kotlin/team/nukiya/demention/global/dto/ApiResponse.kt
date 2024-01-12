package team.nukiya.demention.global.dto

import org.springframework.http.HttpStatus

data class ApiResponse<T>(
    val code: Int,
    val status: HttpStatus,
    val message: String,
    val data: T
)
