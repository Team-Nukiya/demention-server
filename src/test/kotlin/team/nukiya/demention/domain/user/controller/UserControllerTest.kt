package team.nukiya.demention.domain.user.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.JsonFieldType.VARIES
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import team.nukiya.demention.RestDocsTestSupport
import team.nukiya.demention.domain.user.controller.dto.UserSignInRequest
import team.nukiya.demention.domain.user.controller.dto.UserSignUpRequest
import team.nukiya.demention.domain.user.domain.Authority
import team.nukiya.demention.domain.user.domain.UserEntity
import team.nukiya.demention.domain.user.repository.UserEntityRepository
import team.nukiya.demention.global.constant.ApiUrlConstant.USER_URL
import java.util.UUID

class UserControllerTest : RestDocsTestSupport() {
    @Autowired
    private lateinit var userEntityRepository: UserEntityRepository

    @Test
    fun `유저가 회원가입을 한다`() {
        // given
        val userSignUpRequest = UserSignUpRequest(
            phoneNumber = "010xxxxxxxx",
            latitude = "36.3914",
            longitude = "127.3631",
        )

        // when
        val result = mockMvc.perform(
            post("$USER_URL/sign-up")
                .content(objectMapper.writeValueAsString(userSignUpRequest))
                .contentType(MediaType.APPLICATION_JSON)
        )

        // then
        result
            .andExpect(status().isOk)
            .andDo(
                restDocs.document(
                    requestFields(
                        fieldWithPath("phoneNumber").type(STRING).description("전화번호"),
                        fieldWithPath("latitude").type(STRING).description("위도"),
                        fieldWithPath("longitude").type(STRING).description("경도"),
                    ),
                    responseFields(
                        fieldWithPath("accessToken").type(STRING).description("accessToken"),
                        fieldWithPath("refreshToken").type(STRING).description("refreshToken"),
                        fieldWithPath("accessTokenExpiredAt").type(VARIES).description("accessToken 만료 기간"),
                        fieldWithPath("refreshTokenExpiredAt").type(VARIES).description("refreshToken 만료 기간"),
                    )
                ),
            )
    }

    @Test
    fun `유저가 로그인을 한다`() {
        // given
        val phoneNumber = "010xxxxxxxx"
        val userEntity = UserEntity(
            id = UUID.randomUUID(),
            phoneNumber = phoneNumber,
            nickName = "강민",
            addressName = "대전광역시 유성구 장동 23-9 ",
            sido = "대전광역시",
            gungu = "유성구",
            eupMyeonDong = "장동",
            authority = Authority.USER,
        )
        userEntityRepository.save(userEntity)

        val userSignInRequest = UserSignInRequest(
            phoneNumber = phoneNumber,
        )

        // when
        val result = mockMvc.perform(
            post("$USER_URL/sign-in")
                .content(objectMapper.writeValueAsString(userSignInRequest))
                .contentType(MediaType.APPLICATION_JSON)
        )

        // then
        result
            .andExpect(status().isOk)
            .andDo(
                restDocs.document(
                    requestFields(
                        fieldWithPath("phoneNumber").type(STRING).description("전화번호"),
                    ),
                    responseFields(
                        fieldWithPath("accessToken").type(STRING).description("accessToken"),
                        fieldWithPath("refreshToken").type(STRING).description("refreshToken"),
                        fieldWithPath("accessTokenExpiredAt").type(VARIES).description("accessToken 만료 기간"),
                        fieldWithPath("refreshTokenExpiredAt").type(VARIES).description("refreshToken 만료 기간"),
                    ),
                )
            )
    }
}