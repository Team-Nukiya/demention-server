package team.nukiya.demention.domain.help.controller.dto

import team.nukiya.demention.domain.help.domain.AllHelp

data class GetAllHelpsResponse(
    val helps: List<AllHelp>,
)
