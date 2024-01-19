package team.nukiya.demention.domain.help.domain

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import team.nukiya.demention.domain.user.repository.UserEntityRepository
import team.nukiya.demention.global.mapper.GenericMapper

@Component
class HelpMapper(
    private val userEntityRepository: UserEntityRepository,
) : GenericMapper<Help, HelpEntity> {
    override fun toDomain(entity: HelpEntity) =
        Help(
            id = entity.id,
            userId = entity.userEntity!!.id,
            title = entity.title,
            content = entity.content,
            compensation = entity.compensation,
            helpImageUrl = entity.helpImageUrl,
            helpStatus = entity.helpStatus,
            helpStartDateTime = entity.helpStartDateTime,
            helpEndDateTime = entity.helpEndDateTime,
            createdDateTime = entity.createdDateTime,
            modifiedDateTime = entity.modifiedDateTime,
        )

    override fun toEntity(domain: Help): HelpEntity {
        val userEntity = userEntityRepository.findByIdOrNull(domain.userId)
        return HelpEntity(
            id = domain.id,
            userEntity = userEntity,
            title = domain.title,
            content = domain.content,
            compensation = domain.compensation,
            helpImageUrl = domain.helpImageUrl,
            helpStatus = domain.helpStatus,
            helpStartDateTime = domain.helpStartDateTime,
            helpEndDateTime = domain.helpEndDateTime,
        )
    }
}
