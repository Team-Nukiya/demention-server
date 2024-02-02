package team.nukiya.demention.global.dto

class Paging(
    page: Long?,
    size: Long?,
) {
    val page = page ?: DEFAULT_PAGE
    val size: Long = size ?: DEFAULT_LIMIT

    val offset: Long
        get() = page * size

    companion object {
        const val DEFAULT_PAGE = 0L
        const val DEFAULT_LIMIT = 50L
    }
}
