package team.nukiya.demention.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.DELETE
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.PATCH
import org.springframework.http.HttpMethod.POST
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
                it.requestMatchers(GET, "/health-check").permitAll()
                // auth
                it.requestMatchers(POST, "$VERSION$AUTH_URL/codes").permitAll()
                it.requestMatchers(GET, "$VERSION$AUTH_URL/certified").permitAll()
                // user
                it.requestMatchers(POST, "$VERSION$USER_URL/sign-up").permitAll()
                it.requestMatchers(POST, "$VERSION$USER_URL/sign-in").permitAll()
                // help
                it.requestMatchers(POST, "$VERSION$HELP_URL").authenticated()
                it.requestMatchers(PATCH, "$VERSION$HELP_URL$HELP_ID").authenticated()
                it.requestMatchers(DELETE, "$VERSION$HELP_URL$HELP_ID").authenticated()
                it.anyRequest().denyAll()
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
        private const val HELP_URL = "/helps"
        private const val HELP_ID = "/{help-id}"
    }
}
