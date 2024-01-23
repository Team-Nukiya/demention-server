package team.nukiya.demention.domain.support.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import team.nukiya.demention.domain.help.domain.HelpEntity
import team.nukiya.demention.domain.help.domain.HelpStatus
import team.nukiya.demention.domain.help.repository.HelpEntityRepository
import team.nukiya.demention.domain.support.domain.SupportEntity
import team.nukiya.demention.domain.support.domain.SupportStatus
import team.nukiya.demention.domain.support.domain.SupportStatus.SUPPORTED
import team.nukiya.demention.domain.user.domain.Authority
import team.nukiya.demention.domain.user.domain.UserEntity
import team.nukiya.demention.domain.user.repository.UserEntityRepository
import java.time.LocalDateTime
import java.util.UUID

@SpringBootTest
class SupportRepositoryTest {
    @Autowired
    private lateinit var supportRepository: SupportRepository

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
    fun `유저 식별키와 지원 상태 리스트로 지원 엔티티가 존재하는지 확인한다`() {
        // given
        val userEntity = createUserEntity()
        userEntityRepository.save(userEntity)
        val helpEntity = createHelpEntity(userEntity)
        helpEntityRepository.save(helpEntity)
        val supportEntity = createSupportEntity(userEntity, helpEntity, SUPPORTED)
        supportEntityRepository.save(supportEntity)
        val userId = userEntity.id

        // when
        val result = supportRepository.existsByUserIdAndInStatus(userId, listOf(SUPPORTED))

        // then
        assertThat(result).isTrue()
    }

    @Test
    fun `유저, 공고 식별키로 지원 객체를 가져온다`() {
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
        val support = supportRepository.queryByUserIdAndHelpId(userId, helpId)

        // then
        assertThat(support!!.id).isEqualTo(supportEntity.id)
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