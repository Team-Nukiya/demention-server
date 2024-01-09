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
import team.nukiya.demention.global.security.jwt.JwtParser

@Configuration
class SecurityConfig(
    private val jwtParser: JwtParser,
    private val objectMapper: ObjectMapper
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
                    .anyRequest().denyAll()
            }
            .addFilterBefore(JwtFilter(jwtParser), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(GlobalExceptionFilter(objectMapper), JwtFilter::class.java)
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}
