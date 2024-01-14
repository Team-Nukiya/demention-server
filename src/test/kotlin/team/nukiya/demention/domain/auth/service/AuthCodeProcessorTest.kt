package team.nukiya.demention.domain.auth.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import team.nukiya.demention.domain.auth.domain.AuthCode
import team.nukiya.demention.domain.auth.domain.AuthCodeEntity
import team.nukiya.demention.domain.auth.domain.AuthCodeMapper
import team.nukiya.demention.domain.auth.repisitory.AuthCodeEntityRepository

@ExtendWith(MockitoExtension::class)
class AuthCodeProcessorTest {
    @InjectMocks
    private lateinit var authCodeProcessor: AuthCodeProcessor

    @Mock
    private lateinit var authCodeEntityRepository: AuthCodeEntityRepository

    @Mock
    private lateinit var authCodeMapper: AuthCodeMapper

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

        given(authCodeMapper.toEntity(any()))
            .willReturn(authCodeEntity)

        given(authCodeMapper.toDomain(any()))
            .willReturn(authCode)

        given(authCodeEntityRepository.save(any()))
            .willReturn(authCodeEntity)

        // when
        val savedAuthCode = authCodeProcessor.saveAuthCode(authCode)

        // then
        assertThat(savedAuthCode).usingRecursiveComparison().isEqualTo(authCode)
    }
}
