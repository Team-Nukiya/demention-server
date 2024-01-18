package team.nukiya.demention.domain.help.service

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import team.nukiya.demention.domain.help.domain.HelpDetails
import team.nukiya.demention.domain.help.domain.HelpMapper
import team.nukiya.demention.domain.help.domain.QQueryHelpDetailsVO
import team.nukiya.demention.domain.help.domain.QHelpEntity.helpEntity
import team.nukiya.demention.domain.help.repository.HelpEntityRepository
import team.nukiya.demention.domain.user.domain.QUserEntity.userEntity
import java.util.UUID

@Transactional(readOnly = true)
@Component
class HelpReader(
    private val helpEntityRepository: HelpEntityRepository,
    private val helpMapper: HelpMapper,
    private val jpaQueryFactory: JPAQueryFactory,
) {
    fun getHelpById(helpId: UUID) =
        helpEntityRepository.findByIdOrNull(helpId)
            ?.let { helpMapper.toDomain(it) }

    fun getHelpDetailsById(helpId: UUID): HelpDetails? =
        jpaQueryFactory
            .select(
                QQueryHelpDetailsVO(
                    helpEntity.id,
                    helpEntity.title,
                    helpEntity.content,
                    helpEntity.compensation,
                    helpEntity.helpImageUrl,
                    helpEntity.helpStatus,
                    helpEntity.helpStartDateTime,
                    helpEntity.helpEndDateTime,
                    helpEntity.modifiedDateTime,
                    userEntity.addressName,
                    userEntity.nickName,
                )
            )
            .from(helpEntity)
            .join(helpEntity.userEntity, userEntity)
            .fetchOne()
}
