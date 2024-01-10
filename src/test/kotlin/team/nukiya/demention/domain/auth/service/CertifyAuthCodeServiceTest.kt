package team.nukiya.demention.domain.auth.service

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import team.nukiya.demention.domain.auth.domain.AuthCode
import team.nukiya.demention.domain.auth.domain.AuthCodeEntity
import team.nukiya.demention.domain.auth.repisitory.AuthCodeEntityRepository

@SpringBootTest
class CertifyAuthCodeServiceTest @Autowired constructor(
    private val certifyAuthCodeService: CertifyAuthCodeService,
    private val authCodeEntityRepository: AuthCodeEntityRepository,
) {

    @AfterEach
    fun tearDown() {
        authCodeEntityRepository.deleteAll()
    }

    @Test
    fun `유저가 보낸 인증 코드와 전화번호가 올바른지 확인한다`() {
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

        // when & then
        assertDoesNotThrow {
            certifyAuthCodeService.certify(authCode)
        }
    }
}