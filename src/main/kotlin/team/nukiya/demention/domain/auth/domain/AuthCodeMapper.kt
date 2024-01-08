package team.nukiya.demention.domain.auth.domain

import org.springframework.stereotype.Component
import team.nukiya.demention.global.mapper.GenericMapper

@Component
class AuthCodeMapper : GenericMapper<AuthCode, AuthCodeEntity> {
    override fun toDomain(entity: AuthCodeEntity?) =
        entity?.let {
            AuthCode(
                code = it.code,
                phoneNumber = it.phoneNumber,
            )
        }

    override fun toEntity(domain: AuthCode) =
        AuthCodeEntity(
            code = domain.code,
            phoneNumber = domain.phoneNumber,
        )
}