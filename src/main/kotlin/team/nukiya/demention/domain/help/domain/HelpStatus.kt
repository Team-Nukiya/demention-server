package team.nukiya.demention.domain.help.domain

enum class HelpStatus(
    val description: String
) {
    HELPING("모집 중"),
    DONE("모집 완료"),
    ALL("모두"),
}
