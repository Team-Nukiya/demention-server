package team.nukiya.demention.domain.auth.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.any
import org.springframework.data.repository.findByIdOrNull
import team.nukiya.demention.domain.auth.domain.AuthCode
import team.nukiya.demention.domain.auth.domain.AuthCodeEntity
import team.nukiya.demention.domain.auth.domain.AuthCodeMapper
import team.nukiya.demention.domain.auth.exception.AuthCodeNotFoundException
import team.nukiya.demention.domain.auth.repisitory.AuthCodeEntityRepository
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class AuthCodeReaderTest {
    @InjectMocks
    private lateinit var authCodeReader: AuthCodeReader

    @Mock
    private lateinit var authCodeEntityRepository: AuthCodeEntityRepository

    @Mock
    private lateinit var authCodeMapper: AuthCodeMapper

    @Test
    fun `인증 코드로 인증코드 객체를 가져온다`() {
        // given
        val code = "111111"
        val phoneNumber = "010xxxxxxxx"

        val authCodeEntity = AuthCodeEntity(
            code = code,
            phoneNumber = phoneNumber,
        )

        val authCode = AuthCode(
            code = code,
            phoneNumber = phoneNumber
        )

        given(authCodeEntityRepository.findById(anyString()))
            .willReturn(Optional.of(authCodeEntity))

        given(authCodeMapper.toDomain(any()))
            .willReturn(authCode)

        // when
        val savedAuthCode = authCodeReader.getAuthCodeByCode(authCode.code)

        // then
        assertThat(savedAuthCode).usingRecursiveComparison().isEqualTo(authCode)
    }

    @Test
    fun `인증 코드가 다르면 인증코드 객체를 가져오지 못해 예외가 발생한다`() {
        // given
        val code = "111111"

        given(authCodeEntityRepository.findByIdOrNull(anyString()))
            .willAnswer { Optional.ofNullable(null) }

        // when & then
        assertThrows<AuthCodeNotFoundException> {
            authCodeReader.getAuthCodeByCode(code)
        }
    }
}
