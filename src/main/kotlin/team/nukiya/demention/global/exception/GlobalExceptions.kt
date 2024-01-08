package team.nukiya.demention.global.exception

import team.nukiya.demention.global.error.CustomException

object BadRequestException : CustomException(400, "잘못된 요청입니다.")

object MethodNotAllowedException : CustomException(405, "지원하지 않는 Http 메서드입니다.")

object InternalServerErrorException : CustomException(500, "서버 에러가 발생했습니다.")
