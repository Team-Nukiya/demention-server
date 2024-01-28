package team.nukiya.demention.domain.auth.controller

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.JsonFieldType.NUMBER
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.queryParameters
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import team.nukiya.demention.RestDocsTestSupport
import team.nukiya.demention.domain.auth.controller.dto.SendAuthCodeRequest
import team.nukiya.demention.domain.auth.domain.AuthCodeEntity
import team.nukiya.demention.domain.auth.domain.AuthCodeLimitEntity
import team.nukiya.demention.domain.auth.repisitory.AuthCodeEntityRepository
import team.nukiya.demention.domain.auth.repisitory.AuthCodeLimitEntityRepository
import team.nukiya.demention.global.constant.ApiUrlConstant.AUTH_URL

class AuthControllerTest : RestDocsTestSupport() {
    @Autowired
    private lateinit var authCodeEntityRepository: AuthCodeEntityRepository

    @Autowired
    private lateinit var authCodeLimitEntityRepository: AuthCodeLimitEntityRepository

    @AfterEach
    fun tearDown() {
        authCodeLimitEntityRepository.deleteAll()
        authCodeEntityRepository.deleteAll()
    }

    @Test
    fun `인증 코드를 보낸다`() {
        // given
        val sendAuthCodeRequest = SendAuthCodeRequest(
            to = "010xxxxxxxx"
        )

        // when
        val result = mockMvc.perform(
            post("$AUTH_URL/codes")
                .content(objectMapper.writeValueAsString(sendAuthCodeRequest))
                .contentType(MediaType.APPLICATION_JSON)
        )

        // then
        result
            .andExpect(status().isOk)
            .andDo(
                restDocs.document(
                    requestFields(
                        fieldWithPath("to").type(STRING).description("인증 코드 보낼 전화번호"),
                    ),
                    responseFields(
                        fieldWithPath("remainLimitCount").type(NUMBER).description("남은 인증 코드 요청 횟수"),
                        fieldWithPath("code").type(STRING).description("인증코드"),
                    )
                )
            )
    }

    @Test
    fun `올바른 인증코드인지 확인한다`() {
        // given
        val code = "111111"
        val phoneNumber = "010xxxxxxxx"
        authCodeLimitEntityRepository.save(
            AuthCodeLimitEntity(
                phoneNumber = phoneNumber,
                limit = 1,
            )
        )

        authCodeEntityRepository.save(
            AuthCodeEntity(
                code = code,
                phoneNumber = phoneNumber,
            )
        )

        // when
        val result = mockMvc.perform(
            get("$AUTH_URL/certified?code=$code&phone-number=$phoneNumber")
        )

        // then
        result
            .andExpect(status().isOk)
            .andDo(
                restDocs.document(
                    queryParameters(
                        parameterWithName("code").description("인증코드"),
                        parameterWithName("phone-number").description("인증코드 받은 전화번호"),
                    )
                )
            )
    }
}