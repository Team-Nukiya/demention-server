package team.nukiya.demention.domain.support.exception

import team.nukiya.demention.global.error.CustomException

object SupportCanNotCancelException : CustomException(403, "지원을 취소할 수 없습니다.")

object SupportNotFountException : CustomException(404, "지원 내역을 찾지 못했습니다.")

object AlreadySupportException : CustomException(409, "중복 지원이 불가합니다.")