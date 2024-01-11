package team.nukiya.demention.domain.user.domain

import org.springframework.stereotype.Component
import team.nukiya.demention.global.mapper.GenericMapper

@Component
class UserMapper : GenericMapper<User, UserEntity> {
    override fun toDomain(entity: UserEntity) =
        User(
            id = entity.id,
            phoneNumber = entity.phoneNumber,
            nickName = entity.nickName,
            name = entity.name,
            address = Address(
                addressName = entity.addressName,
                sido = entity.sido,
                gungu = entity.gungu,
                eupMyeonDong = entity.eupMyeonDong,
            ),
            authority = entity.authority,
            isDeleted = entity.isDeleted,
        )


    override fun toEntity(domain: User) =
        UserEntity(
            id = domain.id,
            phoneNumber = domain.phoneNumber,
            nickName = domain.nickName,
            name = domain.name,
            addressName = domain.address.addressName,
            sido = domain.address.sido,
            gungu = domain.address.gungu,
            eupMyeonDong = domain.address.eupMyeonDong,
            authority = domain.authority,
            isDeleted = domain.isDeleted,
        )
}
