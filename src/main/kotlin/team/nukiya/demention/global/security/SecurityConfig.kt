package team.nukiya.demention.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import team.nukiya.demention.global.filter.GlobalExceptionFilter
import team.nukiya.demention.global.filter.JwtFilter
import team.nukiya.demention.infrastructure.jwt.JwtParser

@Configuration
class SecurityConfig(
    private val jwtParser: JwtParser,
    private val objectMapper: ObjectMapper,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .cors(Customizer.withDefaults())
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                // health check
                it.requestMatchers(HttpMethod.GET, "/health-check").permitAll()
                // auth
                it.requestMatchers(HttpMethod.POST, "$VERSION$AUTH_URL/codes").permitAll()
                it.requestMatchers(HttpMethod.GET, "$VERSION$AUTH_URL/certified").permitAll()
                // user
                it.requestMatchers(HttpMethod.POST, "$VERSION$USER_URL/sign-up").permitAll()
                it.requestMatchers(HttpMethod.POST, "$VERSION$USER_URL/sign-in").permitAll()
                it.anyRequest().permitAll()
            }
            .addFilterBefore(JwtFilter(jwtParser), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(GlobalExceptionFilter(objectMapper), JwtFilter::class.java)
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    companion object {
        private const val VERSION = "/v1"
        private const val AUTH_URL = "/auth"
        private const val USER_URL = "/users"
    }
}
