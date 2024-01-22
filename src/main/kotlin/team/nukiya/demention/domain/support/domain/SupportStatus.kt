package team.nukiya.demention.domain.support.domain

enum class SupportStatus(
    val description: String
) {
    SUPPORTING("지원"),
    UNSUPPORTING("지원 취소"),
    APPROVED("승인"),
    REJECTED("거절"),
    DONE("완료");

    companion object {
        val RESUPPORT
            get() = listOf(SUPPORTING, APPROVED, REJECTED, DONE)
    }
}
