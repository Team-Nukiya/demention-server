package team.nukiya.demention.domain.support.repository

import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import team.nukiya.demention.domain.help.domain.AllHelp
import team.nukiya.demention.domain.help.domain.QHelpEntity.helpEntity
import team.nukiya.demention.domain.help.domain.QQueryAllHelp
import team.nukiya.demention.domain.support.domain.QSupportEntity.supportEntity
import team.nukiya.demention.domain.support.domain.Support
import team.nukiya.demention.domain.support.domain.SupportMapper
import team.nukiya.demention.domain.support.domain.SupportStatus
import team.nukiya.demention.domain.user.domain.QUserEntity.userEntity
import team.nukiya.demention.domain.user.domain.User
import team.nukiya.demention.global.dto.Paging
import java.util.UUID

@Repository
class SupportRepository(
    private val supportEntityRepository: SupportEntityRepository,
    private val supportMapper: SupportMapper,
    private val jpaQueryFactory: JPAQueryFactory,
) {
    fun save(support: Support): Support =
        supportEntityRepository.save(
            supportMapper.toEntity(support)
        ).let { supportMapper.toDomain(it) }

    fun existsByUserIdAndInStatus(userId: UUID, supportStatus: List<SupportStatus>): Boolean =
        jpaQueryFactory
            .selectOne()
            .from(supportEntity)
            .where(
                supportEntity.userEntity.id.eq(userId),
                supportEntity.supportStatus.`in`(supportStatus),
            )
            .fetchFirst() != null

    fun queryByUserIdAndHelpId(userId: UUID, helpId: UUID): Support? =
        jpaQueryFactory
            .selectFrom(supportEntity)
            .where(
                supportEntity.userEntity.id.eq(userId),
                supportEntity.helpEntity.id.eq(helpId),
            )
            .fetchOne()
            ?.let { supportMapper.toDomain(it) }

    fun queryById(supportId: UUID): Support? =
        supportEntityRepository.findByIdOrNull(supportId)
            ?.let { supportMapper.toDomain(it) }

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
            .from(userEntity)
            .join(supportEntity)
            .on(supportEntity.userEntity.id.eq(user.id))
            .join(supportEntity.helpEntity, helpEntity)
            .offset(paging.offset)
            .limit(paging.size)
            .fetch()
}