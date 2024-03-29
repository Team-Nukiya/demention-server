package team.nukiya.demention.domain.help.service

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import team.nukiya.demention.domain.help.domain.AllHelp
import team.nukiya.demention.domain.help.domain.HelpDetails
import team.nukiya.demention.domain.help.domain.HelpMapper
import team.nukiya.demention.domain.help.domain.HelpStatus
import team.nukiya.demention.domain.help.domain.HelpStatus.ALL
import team.nukiya.demention.domain.help.domain.HelpStatus.DONE
import team.nukiya.demention.domain.help.domain.HelpStatus.HELPING
import team.nukiya.demention.domain.help.domain.QHelpEntity.helpEntity
import team.nukiya.demention.domain.help.domain.QQueryAllHelp
import team.nukiya.demention.domain.help.domain.QQueryHelpDetailsVO
import team.nukiya.demention.domain.help.repository.HelpEntityRepository
import team.nukiya.demention.domain.user.domain.QUserEntity.userEntity
import team.nukiya.demention.domain.user.domain.User
import team.nukiya.demention.global.dto.Paging
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

    fun getAllHelps(
        helpStatus: HelpStatus,
        sido: String,
        paging: Paging,
    ): List<AllHelp> =
        jpaQueryFactory
            .select(
                QQueryAllHelp(
                    helpEntity.id,
                    helpEntity.title,
                    helpEntity.compensation,
                    helpEntity.helpImageUrl,
                    helpEntity.helpStartDateTime,
                    helpEntity.helpEndDateTime,
                    userEntity.addressName,
                    userEntity.nickName,
                )
            )
            .from(helpEntity)
            .join(helpEntity.userEntity, userEntity)
            .where(
                helpStatusEq(helpStatus),
                userEntity.sido.eq(sido),
            )
            .offset(paging.offset)
            .limit(paging.size)
            .fetch()

    fun getHistories(user: User, paging: Paging): List<AllHelp> =
        jpaQueryFactory
            .select(
                QQueryAllHelp(
                    helpEntity.id,
                    helpEntity.title,
                    helpEntity.compensation,
                    helpEntity.helpImageUrl,
                    helpEntity.helpStartDateTime,
                    helpEntity.helpEndDateTime,
                    Expressions.asString(user.address.addressName).`as`(userEntity.addressName),
                    Expressions.asString(user.nickName).`as`(userEntity.nickName),
                )
            )
            .from(helpEntity)
            .join(helpEntity.userEntity, userEntity)
            .on(userEntity.id.eq(user.id))
            .offset(paging.offset)
            .limit(paging.size)
            .fetch()

    private fun helpStatusEq(helpStatus: HelpStatus): BooleanExpression? =
        when (helpStatus) {
            ALL -> helpEntity.helpStatus.`in`(listOf(HELPING, DONE))
            HELPING -> helpEntity.helpStatus.eq(HELPING)
            DONE -> helpEntity.helpStatus.eq(DONE)
        }
}
