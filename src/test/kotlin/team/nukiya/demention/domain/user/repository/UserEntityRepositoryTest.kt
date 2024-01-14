package team.nukiya.demention.domain.user.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import team.nukiya.demention.domain.user.domain.Authority.USER
import team.nukiya.demention.domain.user.domain.UserEntity
import java.util.UUID

// TODO: @DataJpaTest 사용하면 에러남
@SpringBootTest
class UserEntityRepositoryTest {

    @Autowired
    private lateinit var userEntityRepository: UserEntityRepository

    @AfterEach
    fun tearDown() {
        userEntityRepository.deleteAll()
    }

    @Test
    fun `전화번호로 유저 엔티티를 찾는다`() {
        // given
        val phoneNumber = "010xxxxxxxx"
        val userEntity = UserEntity(
            id = UUID.randomUUID(),
            phoneNumber = phoneNumber,
            nickName = "강민",
            name = "강민",
            addressName = "대전광역시 유성구 장동 23-9 ",
            sido = "대전광역시",
            gungu = "유성구",
            eupMyeonDong = "장동",
            authority = USER,
            isDeleted = false,
        )

        userEntityRepository.save(userEntity)

        // when
        val savedUserEntity = userEntityRepository.findByPhoneNumber(phoneNumber)

        // then
        assertThat(savedUserEntity).usingRecursiveComparison().isEqualTo(userEntity)
    }

    @Test
    fun `전화번호로 유저 엔티티가 존재하는지 확인한다`() {
        // given
        val phoneNumber = "010xxxxxxxx"
        val userEntity = UserEntity(
            id = UUID.randomUUID(),
            phoneNumber = phoneNumber,
            nickName = "강민",
            name = "강민",
            addressName = "대전광역시 유성구 장동 23-9 ",
            sido = "대전광역시",
            gungu = "유성구",
            eupMyeonDong = "장동",
            authority = USER,
            isDeleted = false,
        )

        userEntityRepository.save(userEntity)

        // when
        val exists = userEntityRepository.existsByPhoneNumber(phoneNumber)

        // then
        assertThat(exists).isTrue()
    }
}
