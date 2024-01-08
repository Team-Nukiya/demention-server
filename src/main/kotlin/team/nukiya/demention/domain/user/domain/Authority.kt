package team.nukiya.demention.domain.user.domain

enum class Authority(
    val description: String
) {
    USER("사용자"),
    DEVELOPER("개발자"),
}