package team.nukiya.demention.domain.auth.domain

import org.springframework.stereotype.Component
import team.nukiya.demention.global.mapper.GenericMapper

@Component
class AuthCodeLimitMapper : GenericMapper<AuthCodeLimit, AuthCodeLimitEntity> {
    override fun toDomain(entity: AuthCodeLimitEntity) =
        AuthCodeLimit(
            phoneNumber = entity.phoneNumber,
            limit = entity.limit
        )

    override fun toEntity(domain: AuthCodeLimit) =
        AuthCodeLimitEntity(
            phoneNumber = domain.phoneNumber,
            limit = domain.limit,
        )
}
