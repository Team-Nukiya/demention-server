package team.nukiya.demention.domain.support.controller

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import team.nukiya.demention.RestDocsTestSupport
import team.nukiya.demention.domain.help.domain.HelpEntity
import team.nukiya.demention.domain.help.domain.HelpStatus
import team.nukiya.demention.domain.help.repository.HelpEntityRepository
import team.nukiya.demention.domain.support.controller.dto.SupportRequest
import team.nukiya.demention.domain.support.domain.SupportEntity
import team.nukiya.demention.domain.support.domain.SupportStatus.SUPPORTED
import team.nukiya.demention.domain.support.repository.SupportEntityRepository
import team.nukiya.demention.domain.user.domain.Authority.USER
import team.nukiya.demention.domain.user.domain.UserEntity
import team.nukiya.demention.domain.user.repository.UserEntityRepository
import team.nukiya.demention.global.constant.ApiUrlConstant.SUPPORT_URL
import team.nukiya.demention.global.security.auth.AuthDetailsService
import team.nukiya.demention.infrastructure.jwt.JwtProvider
import java.time.LocalDateTime
import java.util.UUID

class SupportControllerTest : RestDocsTestSupport() {
    @Autowired
    private lateinit var userEntityRepository: UserEntityRepository

    @Autowired
    private lateinit var authDetailsService: AuthDetailsService

    @Autowired
    private lateinit var jwtProvider: JwtProvider

    @Autowired
    private lateinit var helpEntityRepository: HelpEntityRepository

    @Autowired
    private lateinit var supportEntityRepository: SupportEntityRepository

    @AfterEach
    fun tearDown() {
        supportEntityRepository.deleteAll()
        helpEntityRepository.deleteAll()
        userEntityRepository.deleteAll()
    }

    @Test
    fun `공고에 지원한다`() {
        // given
        val savedUserEntity = userEntityRepository.save(
            createUserEntity()
        )

        val userId = savedUserEntity.id.toString()
        val provider = authDetailsService.loadUserByUsername(userId)
        val accessToken = jwtProvider.generateAccessToken(userId)

        val savedHelpEntity = helpEntityRepository.save(
            createHelpEntity(savedUserEntity)
        )

        val supportRequest = SupportRequest(
            helpId = savedHelpEntity.id
        )

        // when
        val result = mockMvc.perform(
            post(SUPPORT_URL)
                .header("Authorization", "Bearer $accessToken")
                .content(objectMapper.writeValueAsString(supportRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.user(provider))
        )

        // then
        result
            .andExpect(status().isCreated)
            .andDo(
                restDocs.document(
                    requestHeaders(
                        headerWithName("Authorization").description("JWT 액세스 토큰"),
                    ),
                    requestFields(
                        fieldWithPath("helpId").description("지원할 공고 식별키"),
                    ),
                ),
            )
    }

    @Test
    fun `공고 지원을 취소한다`() {
        // given
        val savedUserEntity = userEntityRepository.save(
            createUserEntity()
        )

        val userId = savedUserEntity.id.toString()
        val provider = authDetailsService.loadUserByUsername(userId)
        val accessToken = jwtProvider.generateAccessToken(userId)

        val savedHelpEntity = helpEntityRepository.save(
            createHelpEntity(savedUserEntity)
        )

        supportEntityRepository.save(
            SupportEntity(
                id = UUID.randomUUID(),
                userEntity = savedUserEntity,
                helpEntity = savedHelpEntity,
                supportStatus = SUPPORTED,
            )
        )

        // when
        val result = mockMvc.perform(
            patch("$SUPPORT_URL/{help-id}", savedHelpEntity.id)
                .header("Authorization", "Bearer $accessToken")
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.user(provider))
        )

        // then
        result
            .andExpect(status().isNoContent)
            .andDo(
                restDocs.document(
                    requestHeaders(
                        headerWithName("Authorization").description("JWT 액세스 토큰"),
                    ),
                    pathParameters(
                        parameterWithName("help-id").description("지원 취소할 공고 식별키"),
                    ),
                ),
            )
    }

    private fun createUserEntity() =
        UserEntity(
            id = UUID.randomUUID(),
            phoneNumber = "010xxxxxxxx",
            nickName = "강민",
            addressName = "대전광역시 유성구 장동 23-9 ",
            sido = "대전광역시",
            gungu = "유성구",
            eupMyeonDong = "장동",
            authority = USER,
        )

    private fun createHelpEntity(savedUserEntity: UserEntity) =
        HelpEntity(
            id = UUID.randomUUID(),
            userEntity = savedUserEntity,
            title = "약이 필요합니다.",
            content = "이러이러해서 약이 필요합니다.",
            compensation = "1만 원",
            helpImageUrl = "이미지 링크",
            helpStatus = HelpStatus.HELPING,
            helpStartDateTime = LocalDateTime.of(2024, 1, 27, 0, 0),
            helpEndDateTime = LocalDateTime.of(2024, 1, 27, 0, 0),
        )
}