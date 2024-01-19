package team.nukiya.demention.global.dto

data class Paging(
    val page: Long,
    val limit: Long,
) {
    companion object {
        const val DEFAULT_PAGE = 0
    }
}
