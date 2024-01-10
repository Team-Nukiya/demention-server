package team.nukiya.demention.domain.auth.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import team.nukiya.demention.domain.auth.domain.AuthCode
import team.nukiya.demention.domain.auth.domain.AuthCodeEntity
import team.nukiya.demention.domain.auth.repisitory.AuthCodeEntityRepository

@SpringBootTest
class AuthCodeProcessorTest @Autowired constructor(
    private val authCodeProcessor: AuthCodeProcessor,
    private val authCodeEntityRepository: AuthCodeEntityRepository,
) {

    @AfterEach
    fun tearDown() {
        authCodeEntityRepository.deleteAll()
    }

    @Test
    fun `인증 코드 엔티티를 저장하고 인증 코드 도메인으로 변환한다`() {
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
            phoneNumber = phoneNumber,
        )

        // when
        val savedAuthCode = authCodeProcessor.saveAuthCode(authCode)

        // then
        assertThat(savedAuthCode).usingRecursiveComparison().isEqualTo(authCode)
    }
}