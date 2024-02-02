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
            .authorizeHttpRequests {authorize -> authorize
                // health check
                .requestMatchers(GET, "/health-check").permitAll()
                // rest docs
                .requestMatchers(GET, "/docs/index.html").permitAll()
                // auth
                .requestMatchers(POST, "$AUTH_URL/codes").permitAll()
                .requestMatchers(GET, "$AUTH_URL/certified").permitAll()
                // user
                .requestMatchers(POST, "$USER_URL/sign-up").permitAll()
                .requestMatchers(POST, "$USER_URL/sign-in").permitAll()
                .requestMatchers(GET, "$USER_URL/my").authenticated()
                // help
                .requestMatchers(POST, HELP_URL).authenticated()
                .requestMatchers(PATCH, "$HELP_URL/{help-id}").authenticated()
                .requestMatchers(DELETE, "$HELP_URL/{help-id}").authenticated()
                .requestMatchers(GET, "$HELP_URL/{help-id}").authenticated()
                .requestMatchers(GET, HELP_URL).authenticated()
                .requestMatchers(GET, "$HELP_URL/histories").authenticated()
                // support
                .requestMatchers(POST, SUPPORT_URL).authenticated()
                .requestMatchers(PATCH, "$SUPPORT_URL/{help-id}").authenticated()
                .requestMatchers(GET, "$SUPPORT_URL/histories").authenticated()
                // point
                .requestMatchers(POST, POINT_URL).authenticated()
                .anyRequest().denyAll()
            }
            .addFilterBefore(JwtFilter(jwtParser), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(GlobalExceptionFilter(objectMapper), JwtFilter::class.java)
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}
