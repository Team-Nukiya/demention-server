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
            address = Address(
                addressName = entity.addressName,
                sido = entity.sido,
                gungu = entity.gungu,
                eupMyeonDong = entity.eupMyeonDong,
            ),
            authority = entity.authority,
            deletedDateTime = entity.deletedDateTime,
        )


    override fun toEntity(domain: User) =
        UserEntity(
            id = domain.id,
            phoneNumber = domain.phoneNumber,
            nickName = domain.nickName,
            addressName = domain.address.addressName,
            sido = domain.address.sido,
            gungu = domain.address.gungu,
            eupMyeonDong = domain.address.eupMyeonDong,
            authority = domain.authority,
            deletedDateTime = domain.deletedDateTime,
        )
}
