package team.nukiya.demention.domain.user.exception

import team.nukiya.demention.global.error.CustomException

object UserNotFoundException : CustomException(404, "유저를 찾지 못했습니다.")

object UserAlreadyExistsException : CustomException(409, "유저가 이미 존재합니다.")