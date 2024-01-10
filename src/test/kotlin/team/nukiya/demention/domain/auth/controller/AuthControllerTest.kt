//package team.nukiya.demention.domain.auth.controller
//
//import org.junit.jupiter.api.Test
//import org.junit.runner.RunWith
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
//import org.springframework.test.context.junit4.SpringRunner
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
//
//@RunWith(SpringRunner::class)
//@AutoConfigureRestDocs
//@AutoConfigureMockMvc
//@WebMvcTest(controllers = [AuthController::class])
//class AuthControllerTest {
//
//    @Autowired
//    private lateinit var mockMvc: MockMvc
//
//    @Test
//    fun `테스트`() {
//        mockMvc.perform(
//            get("$BASE_URL/certified")
//                .queryParam("code", "111111")
//                .queryParam("phone-number", "010xxxxxxxx")
//        )
//            .andExpect(status().isOk)
//    }
//
//    companion object {
//        private const val BASE_URL = "/v1/auth"
//    }
//}