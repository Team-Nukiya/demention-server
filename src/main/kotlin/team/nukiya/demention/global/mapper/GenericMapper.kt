package team.nukiya.demention.global.mapper

interface GenericMapper<D, E> {
    fun toDomain(entity: E?): D?

    fun toEntity(domain: D): E
}
