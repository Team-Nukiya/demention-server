package team.nukiya.demention.domain.support.domain

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import team.nukiya.demention.domain.help.exception.HelpNotFoundException
import team.nukiya.demention.domain.help.repository.HelpEntityRepository
import team.nukiya.demention.domain.user.exception.UserNotFoundException
import team.nukiya.demention.domain.user.repository.UserEntityRepository
import team.nukiya.demention.global.mapper.GenericMapper

@Component
class SupportMapper(
    private val userEntityRepository: UserEntityRepository,
    private val helpEntityRepository: HelpEntityRepository,
) : GenericMapper<Support, SupportEntity> {
    override fun toDomain(entity: SupportEntity): Support =
        Support(
            id = entity.id,
            userId = entity.userEntity!!.id,
            helpId = entity.helpEntity!!.id,
            supportStatus = entity.supportStatus,
            createdDateTime = entity.createdDateTime,
        )

    override fun toEntity(domain: Support): SupportEntity {
        val userEntity = userEntityRepository.findByIdOrNull(domain.userId) ?: throw UserNotFoundException
        val helpEntity = helpEntityRepository.findByIdOrNull(domain.helpId) ?: throw HelpNotFoundException

        return SupportEntity(
            id = domain.id,
            userEntity = userEntity,
            helpEntity = helpEntity,
            supportStatus = domain.supportStatus,
        )
    }
}
