package team.nukiya.demention.domain.auth.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.springframework.data.redis.core.RedisTemplate
import team.nukiya.demention.domain.auth.domain.AuthCode
import team.nukiya.demention.domain.auth.domain.AuthCodeEntity
import team.nukiya.demention.domain.auth.domain.AuthCodeLimit
import team.nukiya.demention.domain.auth.domain.AuthCodeLimitEntity
import team.nukiya.demention.domain.auth.domain.AuthCodeLimitMapper
import team.nukiya.demention.domain.auth.domain.AuthCodeMapper
import team.nukiya.demention.domain.auth.repisitory.AuthCodeEntityRepository
import team.nukiya.demention.domain.auth.repisitory.AuthCodeLimitEntityRepository

@ExtendWith(MockitoExtension::class)
class AuthCodeProcessorTest {
    @InjectMocks
    private lateinit var authCodeProcessor: AuthCodeProcessor

    @Mock
    private lateinit var authCodeEntityRepository: AuthCodeEntityRepository

    @Mock
    private lateinit var authCodeMapper: AuthCodeMapper

    @Mock
    private lateinit var authCodeLimitEntityRepository: AuthCodeLimitEntityRepository

    @Mock
    private lateinit var authCodeLimitMapper: AuthCodeLimitMapper

    @Mock
    private lateinit var restTemplate: RedisTemplate<String, String>

    @Test
    fun `인증 코드 엔티티를 저장하고 인증 코드 도메인으로 변환한다`() {
        // given
        val code = "111111"
        val phoneNumber = "010xxxxxxxx"

        val authCode = AuthCode(
            code = code,
            phoneNumber = phoneNumber,
        )

        val authCodeEntity = AuthCodeEntity(
            code = code,
            phoneNumber = phoneNumber,
        )

        given(authCodeMapper.toDomain(any()))
            .willReturn(authCode)

        given(authCodeMapper.toEntity(any()))
            .willReturn(authCodeEntity)

        given(authCodeEntityRepository.save(any()))
            .willReturn(authCodeEntity)

        // when
        val savedAuthCode = authCodeProcessor.saveAuthCode(authCode)

        // then
        assertThat(savedAuthCode).usingRecursiveComparison().isEqualTo(authCode)
    }

    @Test
    fun `인증 코드 제한 엔티티를 저장하고 인증 코드 제한 도메인으로 변환한다`() {
        // given
        val authCodeLimit = AuthCodeLimit(
            phoneNumber = "010xxxxxxxx",
            limit = 1,
        )

        val authCodeLimitEntity = AuthCodeLimitEntity(
            phoneNumber = "010xxxxxxxx",
            limit = 1,
        )

        given(authCodeLimitMapper.toDomain(any()))
            .willReturn(authCodeLimit)

        given(authCodeLimitMapper.toEntity(any()))
            .willReturn(authCodeLimitEntity)

        given(authCodeLimitEntityRepository.save(any()))
            .willReturn(authCodeLimitEntity)
        // when
        val savedAuthCodeLimit = authCodeProcessor.saveAuthCodeLimit(authCodeLimit)

        // then
        assertThat(savedAuthCodeLimit).usingRecursiveComparison().isEqualTo(authCodeLimit)
    }

    // TODO: incrementLimit 테스트 코드 추가
}
