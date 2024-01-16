package team.nukiya.demention.domain.auth.controller.dto

import team.nukiya.demention.domain.auth.domain.AuthCodeLimit.Companion.LIMIT

class SendAuthCodeResponse(
    limit: Int,
    val code: String,
) {
    val remainLimitCount = LIMIT - limit
}