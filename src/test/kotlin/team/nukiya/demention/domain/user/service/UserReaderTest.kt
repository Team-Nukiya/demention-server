package team.nukiya.demention.domain.user.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.anyString
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import team.nukiya.demention.domain.user.domain.Address
import team.nukiya.demention.domain.user.domain.Authority.USER
import team.nukiya.demention.domain.user.domain.Coordinate
import team.nukiya.demention.domain.user.domain.User
import team.nukiya.demention.domain.user.domain.UserEntity
import team.nukiya.demention.domain.user.domain.UserMapper
import team.nukiya.demention.domain.user.repository.UserEntityRepository
import team.nukiya.demention.infrastructure.client.address.GetAddressService
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class UserReaderTest {

    @InjectMocks
    private lateinit var userReader: UserReader

    @Mock
    private lateinit var userEntityRepository: UserEntityRepository

    @Mock
    private lateinit var userMapper: UserMapper

    @Mock
    private lateinit var getAddressService: GetAddressService

    @Test
    fun `전화번호로 유저 객체를 가져온다`() {
        // given
        val phoneNumber = "010xxxxxxxx"

        val userEntity = createUserEntity()

        val user = createUser()

        given(userEntityRepository.findByPhoneNumber(anyString()))
            .willReturn(userEntity)

        given(userMapper.toDomain(any()))
            .willReturn(user)

        // when
        val savedUser = userReader.getByPhoneNumber(phoneNumber)

        // then
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(user)
    }

    @Test
    fun `전화번호로 유저가 존재하는지 확인한다`() {
        // given
        val phoneNumber = "010xxxxxxxx"

        given(userEntityRepository.existsByPhoneNumber(anyString()))
            .willReturn(true)

        // when
        val result = userReader.existsByPhoneNumber(phoneNumber)

        // then
        assertThat(result).isTrue()
    }

    @Test
    fun `좌표로 지번을 가져온다`() {
        // given
        val coordinate = Coordinate(
            latitude = "36.3914",
            longitude = "127.3631",
        )

        val address = createAddress()

        given(getAddressService.getAddressByCoordinate(any()))
            .willReturn(address)

        // when
        val getAddress = userReader.getAddressByCoordinate(coordinate)

        // then
        assertThat(getAddress).usingRecursiveComparison().isEqualTo(address)
    }

    private fun createUserEntity() = UserEntity(
        id = UUID.randomUUID(),
        phoneNumber = "010xxxxxxxx",
        nickName = "강민",
        name = "강민",
        addressName = "대전광역시 유성구 장동 23-9 ",
        sido = "대전광역시",
        gungu = "유성구",
        eupMyeonDong = "장동",
        authority = USER,
        isDeleted = false,
    )

    private fun createUser() = User(
        id = UUID.randomUUID(),
        phoneNumber = "010xxxxxxxx",
        nickName = "강민",
        name = "강민",
        address = createAddress(),
        authority = USER,
        isDeleted = false,
    )

    private fun createAddress() = Address(
        addressName = "대전광역시 유성구 장동 23-9 ",
        sido = "대전광역시",
        gungu = "유성구",
        eupMyeonDong = "장동",
    )
}
