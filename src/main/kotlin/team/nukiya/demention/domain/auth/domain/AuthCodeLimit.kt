package team.nukiya.demention.domain.auth.domain

import team.nukiya.demention.domain.auth.exception.AuthCodeOverLimitException

data class AuthCodeLimit(
    val phoneNumber: String,
    val limit: Int,
) {

    fun checkOverLimit() {
        if (this.limit > LIMIT) {
            throw AuthCodeOverLimitException
        }
    }

    companion object {
        const val LIMIT = 5
    }
}
