package team.nukiya.demention.domain.help.service

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple.tuple
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import team.nukiya.demention.domain.help.domain.AllHelp
import team.nukiya.demention.domain.help.domain.HelpDetails
import team.nukiya.demention.domain.help.domain.HelpEntity
import team.nukiya.demention.domain.help.domain.HelpStatus.ALL
import team.nukiya.demention.domain.help.domain.HelpStatus.HELPING
import team.nukiya.demention.domain.help.repository.HelpEntityRepository
import team.nukiya.demention.domain.user.domain.Authority
import team.nukiya.demention.domain.user.domain.UserEntity
import team.nukiya.demention.domain.user.repository.UserEntityRepository
import team.nukiya.demention.global.dto.Paging
import java.time.LocalDateTime
import java.util.UUID

@SpringBootTest
class HelpReaderTest {
    @Autowired
    private lateinit var helpReader: HelpReader

    @Autowired
    private lateinit var helpEntityRepository: HelpEntityRepository

    @Autowired
    private lateinit var userEntityRepository: UserEntityRepository

    @AfterEach
    fun tearDown() {
        helpEntityRepository.deleteAll()
        userEntityRepository.deleteAll()
    }

    @Test
    fun `공고 식별자로 공고 상세 조회 객체를 가져온다`() {
        // given
        val userEntity = createUserEntity()
        userEntityRepository.save(userEntity)

        val helpStartDateTime = LocalDateTime.of(2024, 1, 18, 0, 0)
        val helpEndDateTime = LocalDateTime.of(2024, 1, 19, 0, 0)
        val helpEntity = createHelpEntity(
            userEntity = userEntity,
            helpStartDateTime = helpStartDateTime,
            helpEndDateTime = helpEndDateTime,
        )
        val savedHelpEntity = helpEntityRepository.save(helpEntity)

        val helpDetails = createHelpDetails(
            helpId = savedHelpEntity.id,
            helpStartDateTime = helpStartDateTime,
            helpEndDateTime = helpEndDateTime,
            modifiedDateTime = savedHelpEntity.modifiedDateTime
        )

        // when
        val result = helpReader.getHelpDetailsById(helpId = savedHelpEntity.id)

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(helpDetails)
    }

    @Test
    fun `공고 상태, 지역, 페이지, 페이지 개수를 기준으로 공고 리스트를 가져온다`() {
        // given
        val userEntity = createUserEntity()
        userEntityRepository.save(userEntity)

        val helpStartDateTime = LocalDateTime.of(2024, 1, 18, 0, 0)
        val helpEndDateTime = LocalDateTime.of(2024, 1, 19, 0, 0)

        val helpIds = mutableListOf<UUID>()
        repeat(3) {
            val helpEntity = createHelpEntity(
                userEntity = userEntity,
                helpStartDateTime = helpStartDateTime,
                helpEndDateTime = helpEndDateTime,
            )
            val savedHelpEntity = helpEntityRepository.save(helpEntity)
            helpIds.add(savedHelpEntity.id)
        }
        // when
        val savedAllHelps = helpReader.getAllHelps(ALL,"대전광역시", Paging(0,3))

        // then
        assertThat(savedAllHelps)
            .hasSize(3)
            .extracting(
                "id",
                "title",
                "compensation",
                "helpStartDateTime",
                "helpEndDateTime",
                "userAddressName",
                "userNickName"
            )
            .containsExactlyInAnyOrder(
                tuple(helpIds[0], "약이 필요합니다.", "1만 원", helpStartDateTime, helpEndDateTime, "대전광역시 유성구 장동 23-9 ", "강민"),
                tuple(helpIds[1], "약이 필요합니다.", "1만 원", helpStartDateTime, helpEndDateTime, "대전광역시 유성구 장동 23-9 ", "강민"),
                tuple(helpIds[2], "약이 필요합니다.", "1만 원", helpStartDateTime, helpEndDateTime, "대전광역시 유성구 장동 23-9 ", "강민"),
            )

    }

    private fun createAllHelp(
        helpId: UUID,
        helpStartDateTime: LocalDateTime,
        helpEndDateTime: LocalDateTime,
    ) =
        AllHelp(
            id = helpId,
            title = "약이 필요합니다.",
            compensation = "1만 원",
            helpImageUrl = "이미지 링크",
            helpStartDateTime = helpStartDateTime,
            helpEndDateTime = helpEndDateTime,
            userAddressName = "대전광역시 유성구 장동 23-9 ",
            userNickName = "강민",
        )


    private fun createUserEntity() =
        UserEntity(
            id = UUID.randomUUID(),
            phoneNumber = "010xxxxxxxx",
            nickName = "강민",
            addressName = "대전광역시 유성구 장동 23-9 ",
            sido = "대전광역시",
            gungu = "유성구",
            eupMyeonDong = "장동",
            authority = Authority.USER,
        )

    private fun createHelpEntity(
        userEntity: UserEntity,
        helpStartDateTime: LocalDateTime,
        helpEndDateTime: LocalDateTime,
    ) =
        HelpEntity(
            id = UUID.randomUUID(),
            userEntity = userEntity,
            title = "약이 필요합니다.",
            content = "이러이러해서 약이 필요합니다.",
            compensation = "1만 원",
            helpImageUrl = "이미지 링크",
            helpStatus = HELPING,
            helpStartDateTime = helpStartDateTime,
            helpEndDateTime = helpEndDateTime,
        )

    private fun createHelpDetails(
        helpId: UUID,
        helpStartDateTime: LocalDateTime,
        helpEndDateTime: LocalDateTime,
        modifiedDateTime: LocalDateTime,
    ) = HelpDetails(
        id = helpId,
        title = "약이 필요합니다.",
        content = "이러이러해서 약이 필요합니다.",
        compensation = "1만 원",
        helpImageUrl = "이미지 링크",
        helpStatus = HELPING,
        helpStartDateTime = helpStartDateTime,
        helpEndDateTime = helpEndDateTime,
        modifiedDateTime = modifiedDateTime,
        userAddressName = "대전광역시 유성구 장동 23-9 ",
        userNickName = "강민",
    )
}
