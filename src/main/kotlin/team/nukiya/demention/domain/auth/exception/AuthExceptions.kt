package team.nukiya.demention.domain.auth.exception

import team.nukiya.demention.global.error.CustomException

object WrongAuthCodeException : CustomException(401, "잘못된 인증 코드 입니다.")

object AuthCodeNotFoundException : CustomException(404, "인증 코드를 찾지 못했습니다.")
