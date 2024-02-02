package team.nukiya.demention.domain.user.service

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
import team.nukiya.demention.domain.support.repository.SupportEntityRepository
import team.nukiya.demention.domain.user.domain.Authority
import team.nukiya.demention.domain.user.domain.UserEntity
import team.nukiya.demention.domain.user.domain.UserMapper
import team.nukiya.demention.domain.user.repository.UserEntityRepository
import java.time.LocalDateTime
import java.util.UUID

@SpringBootTest
class GetMyInformationServiceTest {
    @Autowired
    private lateinit var getMyInformationService: GetMyInformationService

    @Autowired
    private lateinit var userEntityRepository: UserEntityRepository

    @Autowired
    private lateinit var helpEntityRepository: HelpEntityRepository

    @Autowired
    private lateinit var supportEntityRepository: SupportEntityRepository

    @Autowired
    private lateinit var userMapper: UserMapper

    @AfterEach
    fun tearDown() {
        supportEntityRepository.deleteAll()
        helpEntityRepository.deleteAll()
        userEntityRepository.deleteAll()
    }

    @Test
    fun `자신의 정보를 확인한다`() {
        // given
        val userEntity1 = createUserEntity()
        userEntityRepository.saveAll(listOf(userEntity1))

        val helpEntity1 = createHelpEntity(userEntity1)
        val helpEntity2 = createHelpEntity(userEntity1)
        helpEntityRepository.saveAll(listOf(helpEntity1, helpEntity2))

        val supportEntity1 = createSupportEntity(userEntity1, helpEntity1, SupportStatus.DONE)
        supportEntityRepository.saveAll(listOf(supportEntity1))

        val user = userMapper.toDomain(userEntity1)

        // when
        val result = getMyInformationService.get(user)

        // then
        assertThat(result).isNotNull
        assertThat(result)
            .extracting("userId", "nickName", "addressName", "helpCount", "supportCount")
            .contains(user.id, user.nickName, user.address.addressName, 2L, 1L)
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