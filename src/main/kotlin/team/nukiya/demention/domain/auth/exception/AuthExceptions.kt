package team.nukiya.demention.domain.auth.exception

import team.nukiya.demention.global.error.CustomException

object WrongAuthCodeException : CustomException(401, "잘못된 인증 코드 입니다.")

object AuthCodeNotFoundException : CustomException(404, "인증 코드를 찾지 못했습니다.")

object AuthCodeLimitNotFoundException : CustomException(404, "인증 코드 제한 정보를 찾지 못했습니다.")

object AuthCodeOverLimitException : CustomException(429, "인증 코드 제한 횟수보다 더 많이 요청했습니다.")
