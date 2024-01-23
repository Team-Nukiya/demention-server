package team.nukiya.demention.domain.support.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import team.nukiya.demention.domain.help.domain.HelpEntity
import team.nukiya.demention.domain.help.domain.HelpStatus
import team.nukiya.demention.domain.help.repository.HelpEntityRepository
import team.nukiya.demention.domain.support.domain.Support
import team.nukiya.demention.domain.support.domain.SupportEntity
import team.nukiya.demention.domain.support.domain.SupportStatus
import team.nukiya.demention.domain.support.domain.SupportStatus.CANCELED
import team.nukiya.demention.domain.support.domain.SupportStatus.SUPPORTED
import team.nukiya.demention.domain.support.exception.AlreadySupportException
import team.nukiya.demention.domain.support.exception.SupportCanNotCancelException
import team.nukiya.demention.domain.support.exception.SupportNotFountException
import team.nukiya.demention.domain.support.repository.SupportEntityRepository
import team.nukiya.demention.domain.user.domain.Authority
import team.nukiya.demention.domain.user.domain.UserEntity
import team.nukiya.demention.domain.user.repository.UserEntityRepository
import java.time.LocalDateTime
import java.util.UUID

@SpringBootTest
class SupportServiceTest {
    @Autowired
    private lateinit var supportService: SupportService

    @Autowired
    private lateinit var supportEntityRepository: SupportEntityRepository

    @Autowired
    private lateinit var userEntityRepository: UserEntityRepository

    @Autowired
    private lateinit var helpEntityRepository: HelpEntityRepository

    @AfterEach
    fun tearDown() {
        supportEntityRepository.deleteAll()
        helpEntityRepository.deleteAll()
        userEntityRepository.deleteAll()
    }

    @Test
    fun `유저가 공고 지원을 한다`() {
        // given
        val userEntity = createUserEntity()
        userEntityRepository.save(userEntity)
        val helpEntity = createHelpEntity(userEntity)
        helpEntityRepository.save(helpEntity)
        val userId = userEntity.id
        val helpId = helpEntity.id

        val support = Support(
            id = UUID.randomUUID(),
            userId = userId,
            helpId = helpId,
            supportStatus = SUPPORTED,
        )

        // when
        val savedSupportId = supportService.support(support)

        // then
        assertThat(savedSupportId).isNotNull()
    }

    @Test
    fun `유저가 전에 공고를 지원했어서 다시 공고를 지원을 할 수 없다`() {
        // given
        val userEntity = createUserEntity()
        userEntityRepository.save(userEntity)
        val helpEntity = createHelpEntity(userEntity)
        helpEntityRepository.save(helpEntity)
        val supportEntity = createSupportEntity(userEntity, helpEntity, SUPPORTED)
        supportEntityRepository.save(supportEntity)
        val userId = userEntity.id
        val helpId = helpEntity.id

        val support = Support(
            id = UUID.randomUUID(),
            userId = userId,
            helpId = helpId,
            supportStatus = SUPPORTED,
        )

        // when & then
        assertThrows<AlreadySupportException> {
            supportService.support(support)
        }
    }

    @Test
    fun `유저가 공고 지원을 취소한다`() {
        // given
        val userEntity = createUserEntity()
        userEntityRepository.save(userEntity)
        val helpEntity = createHelpEntity(userEntity)
        helpEntityRepository.save(helpEntity)
        val supportEntity = createSupportEntity(userEntity, helpEntity, SUPPORTED)
        supportEntityRepository.save(supportEntity)

        val userId = userEntity.id
        val helpId = helpEntity.id

        // when
        val savedSupportId = supportService.unSupport(userId, helpId)

        // then
        assertThat(savedSupportId).isNotNull()
    }

    @Test
    fun `지원 상태가 아니여서 공고 지원을 취소할 수 없다`() {
        // given
        val userEntity = createUserEntity()
        userEntityRepository.save(userEntity)
        val helpEntity = createHelpEntity(userEntity)
        helpEntityRepository.save(helpEntity)
        val supportEntity = createSupportEntity(userEntity, helpEntity, CANCELED)
        supportEntityRepository.save(supportEntity)

        val userId = userEntity.id
        val helpId = helpEntity.id

        // when & then
        assertThrows<SupportCanNotCancelException> {
            supportService.unSupport(userId, helpId)
        }
    }

    @Test
    fun `지원 취소할 공고를 찾지 못한다`() {
        // given
        val userEntity = createUserEntity()
        userEntityRepository.save(userEntity)
        val helpEntity = createHelpEntity(userEntity)
        helpEntityRepository.save(helpEntity)
        val supportEntity = createSupportEntity(userEntity, helpEntity, SUPPORTED)
        supportEntityRepository.save(supportEntity)

        val userId = userEntity.id
        val helpId = UUID.randomUUID()

        // when & then
        assertThrows<SupportNotFountException> {
            supportService.unSupport(userId, helpId)
        }
    }

    private fun createSupportEntity(
        userEntity: UserEntity,
        helpEntity: HelpEntity,
        supportStatus: SupportStatus,
    ) = SupportEntity(
        id = UUID.randomUUID(),
        userEntity = userEntity,
        helpEntity = helpEntity,
        supportStatus = supportStatus,
    )

    private fun createUserEntity() =
        UserEntity(
            id = UUID.randomUUID(),
            phoneNumber = "010xxxxxxxx",
            nickName = "강민",
            addressName = "대전광역시 유성구 장동",
            sido = "대전광역시",
            gungu = "유성구",
            eupMyeonDong = "장동",
            authority = Authority.USER,
        )

    private fun createHelpEntity(userEntity: UserEntity) =
        HelpEntity(
            id = UUID.randomUUID(),
            userEntity = userEntity,
            title = "약이 필요합니다.",
            content = "이러이러해서 약이 필요합니다.",
            compensation = "1만 원",
            helpImageUrl = "이미지 링크",
            helpStatus = HelpStatus.HELPING,
            helpStartDateTime = LocalDateTime.now(),
            helpEndDateTime = LocalDateTime.now(),
        )
}