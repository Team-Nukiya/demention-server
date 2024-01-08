package team.nukiya.demention.global.error

abstract class CustomException(
    val status: Int,
    override val message: String
) : RuntimeException()
