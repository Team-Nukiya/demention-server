package team.nukiya.demention.domain.help.controller

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.JsonFieldType.ARRAY
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.JsonFieldType.VARIES
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.restdocs.request.RequestDocumentation.queryParameters
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import team.nukiya.demention.RestDocsTestSupport
import team.nukiya.demention.domain.help.controller.dto.CreateHelpRequest
import team.nukiya.demention.domain.help.controller.dto.UpdateHelpRequest
import team.nukiya.demention.domain.help.domain.HelpEntity
import team.nukiya.demention.domain.help.domain.HelpStatus
import team.nukiya.demention.domain.help.repository.HelpEntityRepository
import team.nukiya.demention.domain.user.domain.Authority
import team.nukiya.demention.domain.user.domain.UserEntity
import team.nukiya.demention.domain.user.repository.UserEntityRepository
import team.nukiya.demention.global.constant.ApiUrlConstant.HELP_URL
import team.nukiya.demention.global.security.auth.AuthDetailsService
import team.nukiya.demention.infrastructure.jwt.JwtProvider
import java.time.LocalDateTime
import java.util.UUID

class HelpControllerTest : RestDocsTestSupport() {
    @Autowired
    private lateinit var userEntityRepository: UserEntityRepository

    @Autowired
    private lateinit var authDetailsService: AuthDetailsService

    @Autowired
    private lateinit var jwtProvider: JwtProvider

    @Autowired
    private lateinit var helpEntityRepository: HelpEntityRepository

    @AfterEach
    fun tearDown() {
        helpEntityRepository.deleteAll()
        userEntityRepository.deleteAll()
    }

    @Test
    fun `공고를 작성한다`() {
        // given
        val savedUserEntity = userEntityRepository.save(
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
        )

        val userId = savedUserEntity.id.toString()
        val provider = authDetailsService.loadUserByUsername(userId)
        val accessToken = jwtProvider.generateAccessToken(userId)

        val createHelpRequest = CreateHelpRequest(
            title = "도움이 필요합니다.",
            content = "이러이러한 상황입니다.",
            compensation = "10만 원",
            helpImageUrl = "https://~~~",
            helpStartDateTime = LocalDateTime.of(2024, 1, 27, 0, 0),
            helpEndDateTime = LocalDateTime.of(2024, 1, 27, 0, 0),
        )

        // when
        val result = mockMvc.perform(
            post(HELP_URL)
                .header("Authorization", "Bearer $accessToken")
                .content(objectMapper.writeValueAsString(createHelpRequest))
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
                        fieldWithPath("title").type(STRING).description("제목"),
                        fieldWithPath("content").type(STRING).description("내용"),
                        fieldWithPath("compensation").type(STRING).description("보상 내용"),
                        fieldWithPath("helpImageUrl").type(STRING).description("이미지 URL"),
                        fieldWithPath("helpStartDateTime").type(VARIES).description("구인 시작 시간"),
                        fieldWithPath("helpEndDateTime").type(VARIES).description("구인 마감 시간"),
                    ),
                ),
            )
    }

    @Test
    fun `공고를 수정한다`() {
        // given
        val savedUserEntity = userEntityRepository.save(
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
        )

        val userId = savedUserEntity.id.toString()
        val provider = authDetailsService.loadUserByUsername(userId)
        val accessToken = jwtProvider.generateAccessToken(userId)

        val savedHelpEntity = helpEntityRepository.save(
            createHelpEntity(savedUserEntity)
        )

        val updateHelpRequest = UpdateHelpRequest(
            title = "도움이 필요합니다.",
            content = "이러이러한 상황입니다.",
            compensation = "10만 원",
            helpImageUrl = "https://~~~",
            helpStartDateTime = LocalDateTime.of(2024, 1, 27, 0, 0),
            helpEndDateTime = LocalDateTime.of(2024, 1, 27, 0, 0),
        )

        // when
        val result = mockMvc.perform(
            patch("$HELP_URL/{help-id}", savedHelpEntity.id)
                .header("Authorization", "Bearer $accessToken")
                .content(objectMapper.writeValueAsString(updateHelpRequest))
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
                        parameterWithName("help-id").description("공고 식별키")
                    ),
                    requestFields(
                        fieldWithPath("title").type(STRING).description("제목"),
                        fieldWithPath("content").type(STRING).description("내용"),
                        fieldWithPath("compensation").type(STRING).description("보상 내용"),
                        fieldWithPath("helpImageUrl").type(STRING).description("이미지 URL"),
                        fieldWithPath("helpStartDateTime").type(VARIES).description("구인 시작 시간"),
                        fieldWithPath("helpEndDateTime").type(VARIES).description("구인 마감 시간"),
                    ),
                ),
            )
    }

    @Test
    fun `공고를 삭제한다`() {
        // given
        val savedUserEntity = userEntityRepository.save(
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
        )

        val userId = savedUserEntity.id.toString()
        val provider = authDetailsService.loadUserByUsername(userId)
        val accessToken = jwtProvider.generateAccessToken(userId)

        val savedHelpEntity = helpEntityRepository.save(
            createHelpEntity(savedUserEntity)
        )

        // when
        val result = mockMvc.perform(
            delete("$HELP_URL/{help-id}", savedHelpEntity.id)
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
                        parameterWithName("help-id").description("공고 식별키")
                    ),
                ),
            )
    }

    @Test
    fun `공고를 상세 조회한다`() {
        // given
        val savedUserEntity = userEntityRepository.save(
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
        )

        val userId = savedUserEntity.id.toString()
        val accessToken = jwtProvider.generateAccessToken(userId)

        val savedHelpEntity = helpEntityRepository.save(
            createHelpEntity(savedUserEntity)
        )

        // when
        val result = mockMvc.perform(
            get("$HELP_URL/{help-id}", savedHelpEntity.id)
                .header("Authorization", "Bearer $accessToken")
                .contentType(MediaType.APPLICATION_JSON)
        )

        // then
        result
            .andExpect(status().isOk)
            .andDo(
                restDocs.document(
                    requestHeaders(
                        headerWithName("Authorization").description("JWT 액세스 토큰"),
                    ),
                    pathParameters(
                        parameterWithName("help-id").description("공고 식별키")
                    ),
                    responseFields(
                        fieldWithPath("helpId").type(STRING).description("공고 식별키"),
                        fieldWithPath("title").type(STRING).description("제목"),
                        fieldWithPath("content").type(STRING).description("내용"),
                        fieldWithPath("compensation").type(STRING).description("보상 내용"),
                        fieldWithPath("helpImageUrl").type(STRING).description("이미지 URL"),
                        fieldWithPath("helpStatus").type(STRING).description("공고 모집 상태"),
                        fieldWithPath("helpStartDateTime").type(VARIES).description("구인 시작 시간"),
                        fieldWithPath("helpEndDateTime").type(VARIES).description("구인 마감 시간"),
                        fieldWithPath("modifiedDateTime").type(VARIES).description("구인 마감 시간"),
                        fieldWithPath("userAddressName").type(STRING).description("공고 작성자 주소"),
                        fieldWithPath("userNickName").type(STRING).description("공고 작성자 닉네임"),
                    ),
                ),
            )
    }

    @Test
    fun `공고 목록을 조회한다`() {
        // given
        val savedUserEntity = userEntityRepository.save(
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
        )

        val userId = savedUserEntity.id.toString()
        val provider = authDetailsService.loadUserByUsername(userId)
        val accessToken = jwtProvider.generateAccessToken(userId)

        helpEntityRepository.saveAll(
            listOf(
                createHelpEntity(savedUserEntity),
                createHelpEntity(savedUserEntity),
                createHelpEntity(savedUserEntity),
                createHelpEntity(savedUserEntity),
                createHelpEntity(savedUserEntity),
            )
        )

        // when
        val result = mockMvc.perform(
            get("$HELP_URL?help-status=HELPING&page=0&limit=10")
                .header("Authorization", "Bearer $accessToken")
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.user(provider))
        )

        // then
        result
            .andExpect(status().isOk)
            .andDo(
                restDocs.document(
                    requestHeaders(
                        headerWithName("Authorization").description("JWT 액세스 토큰"),
                    ),
                    queryParameters(
                        parameterWithName("help-status").description("공고 상태"),
                        parameterWithName("page").description("페이지 번호"),
                        parameterWithName("limit").description("페이지 사이즈"),
                    ),
                    responseFields(
                        fieldWithPath("helps").type(ARRAY).description("공고 목록"),
                        fieldWithPath("helps[].helpId").type(STRING).description("공고 식별키"),
                        fieldWithPath("helps[].title").type(STRING).description("제목"),
                        fieldWithPath("helps[].compensation").type(STRING).description("보상 내용"),
                        fieldWithPath("helps[].helpImageUrl").type(STRING).description("이미지 URL"),
                        fieldWithPath("helps[].helpStartDateTime").type(VARIES).description("구인 시작 시간"),
                        fieldWithPath("helps[].helpEndDateTime").type(VARIES).description("구인 마감 시간"),
                        fieldWithPath("helps[].userAddressName").type(STRING).description("공고 작성자 주소"),
                        fieldWithPath("helps[].userNickName").type(STRING).description("공고 작성자 닉네임"),
                    ),
                ),
            )
    }

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