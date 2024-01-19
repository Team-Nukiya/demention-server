package team.nukiya.demention.domain.support.domain

enum class SupportStatus(
    val description: String
) {
    SUPPORTING("지원"),
    APPROVED("승인"),
    REJECTED("거절"),
}
