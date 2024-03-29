package team.nukiya.demention.global.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import team.nukiya.demention.infrastructure.jwt.JwtConstant.HEADER
import team.nukiya.demention.infrastructure.jwt.JwtConstant.PREFIX
import team.nukiya.demention.infrastructure.jwt.JwtParser

class JwtFilter(
    private val jwtParser: JwtParser
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = getToken(request)
        token?.let {
            SecurityContextHolder.getContext().authentication = jwtParser.getAuthentication(token)
        }
        filterChain.doFilter(request, response)
    }

    private fun getToken(request: HttpServletRequest): String? {
        val token = request.getHeader(HEADER)

        return if (token != null && token.startsWith(PREFIX)) {
            token.substring(PREFIX.length)
        } else {
            null
        }
    }
}
