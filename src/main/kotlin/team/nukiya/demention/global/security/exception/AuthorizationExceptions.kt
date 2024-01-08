package team.nukiya.demention.global.security.exception

import team.nukiya.demention.global.error.CustomException


object ExpiredTokenException : CustomException(401, "만료된 JWT입니다.")
object InvalidTokenException : CustomException(401, "잘못된 JWT 토큰입니다.")
