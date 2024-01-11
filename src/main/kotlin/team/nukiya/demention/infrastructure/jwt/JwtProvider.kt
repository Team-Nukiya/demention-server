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
    fun generateAllToken(id: String, authority: Authority): TokenResponse {
        val accessToken = generateAccessToken(id, authority)
        val refreshToken = generateRefreshToken(id, authority)

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

    fun generateAccessToken(id: String, authority: Authority) = generateToken(
        id,
        authority,
        ACCESS,
        jwtProperties.accessExp
    )

    fun generateRefreshToken(id: String, authority: Authority) = generateToken(
        id,
        authority,
        REFRESH,
        jwtProperties.refreshExp
    )

    private fun generateToken(id: String, authority: Authority, type: String, exp: Long) =
        Jwts.builder()
            .setHeaderParam(Header.JWT_TYPE, type)
            .setSubject(id)
            .claim(AUTHORITY, authority.name)
            .signWith(jwtProperties.secretKey, SignatureAlgorithm.HS256)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + exp * 1000))
            .compact()
}
