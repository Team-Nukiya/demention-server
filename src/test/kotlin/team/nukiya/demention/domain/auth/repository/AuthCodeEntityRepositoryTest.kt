package team.nukiya.demention.domain.auth.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import team.nukiya.demention.domain.auth.domain.AuthCodeEntity
import team.nukiya.demention.domain.auth.repisitory.AuthCodeEntityRepository

@SpringBootTest
class AuthCodeEntityRepositoryTest @Autowired constructor(
    private val authCodeEntityRepository: AuthCodeEntityRepository,
) {

    @AfterEach
    fun tearDown() {
        authCodeEntityRepository.deleteAll()
    }

    @Test
    fun `인증 코드와 전화번호로 인증코드 엔티티를 찾는다`() {
        // given
        val code = "111111"
        val phoneNumber = "010xxxxxxxx"
        val authCodeEntity = AuthCodeEntity(
            code = code,
            phoneNumber = phoneNumber
        )
        authCodeEntityRepository.save(authCodeEntity)

        // when
        val savedAuthCodeEntity = authCodeEntityRepository.findByCodeAndPhoneNumber(code, phoneNumber)

        // then
        assertThat(savedAuthCodeEntity).usingRecursiveComparison().isEqualTo(authCodeEntity)
    }
}
