package team.nukiya.demention.domain.user.domain

import org.springframework.stereotype.Component
import team.nukiya.demention.global.mapper.GenericMapper

@Component
class UserMapper : GenericMapper<User, UserEntity> {
    override fun toDomain(entity: UserEntity?) =
        entity?.let {
            User(
                id = it.id,
                phoneNumber = it.phoneNumber,
                nickName = it.nickName,
                name = it.name,
                area = it.area,
                authority = it.authority,
                isDeleted = it.isDeleted,
            )
        }

    override fun toEntity(domain: User) =
        UserEntity(
            id = domain.id,
            phoneNumber = domain.phoneNumber,
            nickName = domain.nickName,
            name = domain.name,
            area = domain.area,
            authority = domain.authority,
            isDeleted = domain.isDeleted,
        )
}