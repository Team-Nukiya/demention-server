package team.nukiya.demention.domain.help.exception

import team.nukiya.demention.global.error.CustomException

object FailedIdentityVerification : CustomException(403, "본인 인증에 실패했습니다.")

object MyHelpCanNotSupportException : CustomException(403, "자신의 공고에는 지원할 수 없습니다.")

object HelpNotFoundException : CustomException(404, "봉사 공고를 찾지 못했습니다.")
