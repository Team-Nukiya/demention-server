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
import team.nukiya.demention.global.constant.ApiUrlConstant.AUTH_URL
import team.nukiya.demention.global.constant.ApiUrlConstant.HELP_URL
import team.nukiya.demention.global.constant.ApiUrlConstant.POINT_URL
import team.nukiya.demention.global.constant.ApiUrlConstant.SUPPORT_URL
import team.nukiya.demention.global.constant.ApiUrlConstant.USER_URL
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
                // rest docs
                it.requestMatchers(GET, "/docs/index.html").permitAll()
                // auth
                it.requestMatchers(POST, "$AUTH_URL/codes").permitAll()
                it.requestMatchers(GET, "$AUTH_URL/certified").permitAll()
                // user
                it.requestMatchers(POST, "$USER_URL/sign-up").permitAll()
                it.requestMatchers(POST, "$USER_URL/sign-in").permitAll()
                it.requestMatchers(GET, "$USER_URL/my").authenticated()
                // help
                it.requestMatchers(POST, HELP_URL).authenticated()
                it.requestMatchers(PATCH, "$HELP_URL/{help-id}").authenticated()
                it.requestMatchers(DELETE, "$HELP_URL/{help-id}").authenticated()
                it.requestMatchers(GET, "$HELP_URL/{help-id}").authenticated()
                it.requestMatchers(GET, HELP_URL).authenticated()
                it.requestMatchers(GET, "$HELP_URL/histories").authenticated()
                // support
                it.requestMatchers(POST, SUPPORT_URL).authenticated()
                it.requestMatchers(PATCH, "$SUPPORT_URL/{help-id}").authenticated()
                it.requestMatchers(GET, "$SUPPORT_URL/histories").authenticated()
                // point
                it.requestMatchers(POST, POINT_URL).authenticated()
                it.anyRequest().denyAll()
            }
            .addFilterBefore(JwtFilter(jwtParser), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(GlobalExceptionFilter(objectMapper), JwtFilter::class.java)
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}
