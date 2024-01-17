package team.nukiya.demention.domain.user.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import team.nukiya.demention.domain.user.domain.User.Companion.NICK_NAME_LENGTH

class UserTest {
    @Test
    fun `길이가 10인 랜덤 닉네임을 생성한다`() {
        // when
        val randomNickName = User.generateRandomNickName()

        // then
        assertThat(randomNickName).isNotBlank().hasSize(NICK_NAME_LENGTH)
    }
}
