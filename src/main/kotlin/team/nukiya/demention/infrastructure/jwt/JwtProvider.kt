package team.nukiya.demention.infrastructure.jwt

import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import team.nukiya.demention.domain.auth.domain.RefreshTokenEntity
import team.nukiya.demention.domain.auth.repisitory.RefreshTokenEntityRepository
import team.nukiya.demention.domain.user.controller.dto.TokenResponse
import team.nukiya.demention.domain.user.domain.Authority
import team.nukiya.demention.infrastructure.jwt.JwtConstant.ACCESS
import team.nukiya.demention.infrastructure.jwt.JwtConstant.AUTHORITY
import team.nukiya.demention.infrastructure.jwt.JwtConstant.REFRESH
import java.time.LocalDateTime
import java.util.Date

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties,
    private val refreshTokenEntityRepository: RefreshTokenEntityRepository,
) {
    fun generateAllToken(id: String): TokenResponse {
        val accessToken = generateAccessToken(id)
        val refreshToken = generateRefreshToken(id)

        refreshTokenEntityRepository.save(
            RefreshTokenEntity(
                accountId = id,
                token = refreshToken
            )
        )

        return TokenResponse(
            accessToken,
            refreshToken,
            LocalDateTime.now().plusSeconds(jwtProperties.accessExp),
            LocalDateTime.now().plusSeconds(jwtProperties.refreshExp)
        )
    }

    fun generateAccessToken(id: String) = generateToken(
        id = id,
        type = ACCESS,
        exp = jwtProperties.accessExp
    )

    fun generateRefreshToken(id: String) = generateToken(
        id = id,
        type = REFRESH,
        exp = jwtProperties.refreshExp
    )

    private fun generateToken(id: String, type: String, exp: Long) =
        Jwts.builder()
            .setHeaderParam(Header.JWT_TYPE, type)
            .setSubject(id)
            .signWith(jwtProperties.secretKey, SignatureAlgorithm.HS256)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + exp * 1000))
            .compact()
}
