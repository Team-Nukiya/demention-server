package team.nukiya.demention.domain.auth.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import team.nukiya.demention.domain.auth.domain.AuthCode
import team.nukiya.demention.domain.auth.domain.AuthCodeEntity
import team.nukiya.demention.domain.auth.exception.AuthCodeNotFoundException
import team.nukiya.demention.domain.auth.repisitory.AuthCodeEntityRepository

@SpringBootTest
class AuthCodeReaderTest(
    @Autowired
    private val authCodeReader: AuthCodeReader,
    @Autowired
    private val authCodeEntityRepository: AuthCodeEntityRepository,
) {

    @AfterEach
    fun tearDown() {
        authCodeEntityRepository.deleteAll()
    }

    @Test
    fun `인증 코드와 전화번호로 인증코드 객체를 가져온다`() {
        // given
        val code = "111111"
        val phoneNumber = "010xxxxxxxx"
        val authCodeEntity = AuthCodeEntity(
            code = code,
            phoneNumber = phoneNumber,
        )
        authCodeEntityRepository.save(authCodeEntity)

        val authCode = AuthCode(
            code = code,
            phoneNumber = phoneNumber
        )

        // when
        val savedAuthCode = authCodeReader.getAuthCodeByCodeAndPhoneNumber(authCode)

        // then
        assertThat(savedAuthCode).usingRecursiveComparison().isEqualTo(authCode)
    }

    @Test
    fun `인증 코드와 전화번호가 다르면 인증코드 객체를 가져오지 못해 예외가 발생한다`() {
        // given
        val code = "111111"
        val phoneNumber = "010xxxxxxxx"
        val authCodeEntity = AuthCodeEntity(
            code = code,
            phoneNumber = phoneNumber,
        )
        authCodeEntityRepository.save(authCodeEntity)

        val authCode = AuthCode(
            code = "222222",
            phoneNumber = "010oooooooo"
        )

        // when & then
        assertThrows<AuthCodeNotFoundException> {
            authCodeReader.getAuthCodeByCodeAndPhoneNumber(authCode)
        }
    }

    @Test
    fun `인증 코드가 다르면 인증코드 객체를 가져오지 못해 예외가 발생한다`() {
        // given
        val code = "111111"
        val phoneNumber = "010xxxxxxxx"
        val authCodeEntity = AuthCodeEntity(
            code = code,
            phoneNumber = phoneNumber,
        )
        authCodeEntityRepository.save(authCodeEntity)

        val authCode = AuthCode(
            code = "222222",
            phoneNumber = phoneNumber
        )

        // when & then
        assertThrows<AuthCodeNotFoundException> {
            authCodeReader.getAuthCodeByCodeAndPhoneNumber(authCode)
        }
    }

    @Test
    fun `전화번호가 다르면 인증코드 객체를 가져오지 못해 예외가 발생한다`() {
        // given
        val code = "111111"
        val phoneNumber = "010xxxxxxxx"
        val authCodeEntity = AuthCodeEntity(
            code = code,
            phoneNumber = phoneNumber,
        )
        authCodeEntityRepository.save(authCodeEntity)

        val authCode = AuthCode(
            code = code,
            phoneNumber = "010oooooooo"
        )
        // when & then
        assertThrows<AuthCodeNotFoundException> {
            authCodeReader.getAuthCodeByCodeAndPhoneNumber(authCode)
        }
    }
}
