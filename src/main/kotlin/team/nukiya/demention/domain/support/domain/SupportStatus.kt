package team.nukiya.demention.domain.support.domain

enum class SupportStatus(
    val description: String
) {
    SUPPORTED("지원"),
    CANCELED("지원 취소"),
    APPROVED("승인"),
    REJECTED("거절"),
    DONE("봉사 완료");

    companion object {
        val RESUPPORT
            get() = listOf(SUPPORTED, APPROVED)
    }
}
