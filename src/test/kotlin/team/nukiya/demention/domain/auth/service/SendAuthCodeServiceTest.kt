//package team.nukiya.demention.domain.auth.service
//
//import org.junit.jupiter.api.Test
//import org.mockito.BDDMockito
//import org.mockito.InjectMocks
//import org.mockito.Mock
//import org.mockito.Mockito.any
//import org.mockito.Mockito.anyString
//import org.mockito.Mockito.verify
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import team.nukiya.demention.domain.auth.domain.AuthCode
//
//@SpringBootTest
//class SendAuthCodeServiceTest @Autowired constructor(
//    @InjectMocks
//    private val sendAuthCodeService: SendAuthCodeService,
//    @Mock
//    private val authCodeProcessor: AuthCodeProcessor,
//) {
//
//    @Test
//    fun `테스트`() {
//        // given
//        val to = "010xxxxxxxx"
//
//        val authCode = AuthCode(
//            code = "111111",
//            phoneNumber = to
//        )
//
//        BDDMockito.given(sendAuthCodeService.send(anyString()))
//            .willReturn(any())
//
//        // when
//        sendAuthCodeService.send(to)
//
//        // then
//        verify(authCodeProcessor).saveAuthCode(authCode)
//    }
//}