package team.nukiya.demention.domain.point.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import team.nukiya.demention.domain.help.domain.HelpEntity
import team.nukiya.demention.domain.help.domain.HelpStatus
import team.nukiya.demention.domain.help.repository.HelpEntityRepository
import team.nukiya.demention.domain.point.domain.Point
import team.nukiya.demention.domain.point.repository.PointEntityRepository
import team.nukiya.demention.domain.support.domain.SupportEntity
import team.nukiya.demention.domain.support.domain.SupportStatus
import team.nukiya.demention.domain.support.domain.SupportStatus.DONE
import team.nukiya.demention.domain.support.repository.SupportEntityRepository
import team.nukiya.demention.domain.user.domain.Authority
import team.nukiya.demention.domain.user.domain.UserEntity
import team.nukiya.demention.domain.user.repository.UserEntityRepository
import java.time.LocalDateTime
import java.util.UUID

@SpringBootTest
class PointServiceTest {
    @Autowired
    private lateinit var pointService: PointService

    @Autowired
    private lateinit var pointEntityRepository: PointEntityRepository

    @Autowired
    private lateinit var userEntityRepository: UserEntityRepository

    @Autowired
    private lateinit var helpEntityRepository: HelpEntityRepository

    @Autowired
    private lateinit var supportEntityRepository: SupportEntityRepository

    @AfterEach
    fun tearDown() {
        pointEntityRepository.deleteAll()
        supportEntityRepository.deleteAll()
        helpEntityRepository.deleteAll()
        userEntityRepository.deleteAll()
    }

    @Test
    fun `평점을 부여한다`() {
        // given
        val userEntity = createUserEntity()
        userEntityRepository.save(userEntity)
        val helpEntity = createHelpEntity(userEntity)
        helpEntityRepository.save(helpEntity)
        val supportEntity = createSupportEntity(userEntity, helpEntity, DONE)
        supportEntityRepository.save(supportEntity)

        val point = Point(
            id = UUID.randomUUID(),
            giveUserId = userEntity.id,
            receiveSupportId = supportEntity.id,
            point = 5.0f,
            createdDateTime = LocalDateTime.now()
        )

        // when
        val savedPointId = pointService.give(point)

        // then
        assertThat(savedPointId).isNotNull()
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