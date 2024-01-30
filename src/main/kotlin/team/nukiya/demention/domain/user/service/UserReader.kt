package team.nukiya.demention.domain.user.service

import com.querydsl.core.group.GroupBy.groupBy
import com.querydsl.core.group.GroupBy.list
import com.querydsl.core.types.ExpressionUtils
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import team.nukiya.demention.domain.help.domain.QHelpEntity.helpEntity
import team.nukiya.demention.domain.help.domain.QQueryAllHelp
import team.nukiya.demention.domain.support.domain.QSupportEntity.supportEntity
import team.nukiya.demention.domain.user.domain.Coordinate
import team.nukiya.demention.domain.user.domain.QQueryUserInformation
import team.nukiya.demention.domain.user.domain.QUserEntity.userEntity
import team.nukiya.demention.domain.user.domain.User
import team.nukiya.demention.domain.user.domain.UserInformation
import team.nukiya.demention.domain.user.domain.UserMapper
import team.nukiya.demention.domain.user.repository.UserEntityRepository
import team.nukiya.demention.infrastructure.client.address.GetAddressService

@Transactional(readOnly = true)
@Component
class UserReader(
    private val userEntityRepository: UserEntityRepository,
    private val userMapper: UserMapper,
    private val getAddressService: GetAddressService,
    private val jpaQueryFactory: JPAQueryFactory,
) {
    fun getByPhoneNumber(phoneNumber: String) =
        userEntityRepository.findByPhoneNumber(phoneNumber)?.let {
            userMapper.toDomain(it)
        }

    fun existsByPhoneNumber(phoneNumber: String) =
        userEntityRepository.existsByPhoneNumber(phoneNumber)

    fun getAddressByCoordinate(coordinate: Coordinate) =
        getAddressService.getAddressByCoordinate(coordinate)

    fun getInformation(user: User): UserInformation? =
        jpaQueryFactory
            .selectFrom(userEntity)
            .join(helpEntity)
            .on(helpEntity.userEntity.id.eq(user.id))
            .join(supportEntity)
            .on(supportEntity.userEntity.id.eq(user.id))
            .where(userEntity.id.eq(user.id))
            .transform(
                groupBy(userEntity.id)
                    .list(
                        QQueryUserInformation(
                            Expressions.constantAs(user.id, userEntity.id),
                            Expressions.asString(user.nickName).`as`(userEntity.nickName),
                            Expressions.asString(user.address.addressName).`as`(userEntity.addressName),
                            list(
                                QQueryAllHelp(
                                    helpEntity.id,
                                    helpEntity.title,
                                    helpEntity.compensation,
                                    helpEntity.helpImageUrl,
                                    helpEntity.helpStartDateTime,
                                    helpEntity.helpEndDateTime,
                                    helpEntity.userEntity.addressName,
                                    helpEntity.userEntity.nickName,
                                )
                            ),
                            ExpressionUtils.`as`(
                                JPAExpressions.select(helpEntity.count())
                                    .from(helpEntity)
                                    .where(helpEntity.userEntity.id.eq(user.id)),
                                "helpCount"
                            ),
                            list(
                                QQueryAllHelp(
                                    supportEntity.helpEntity.id,
                                    supportEntity.helpEntity.title,
                                    supportEntity.helpEntity.compensation,
                                    supportEntity.helpEntity.helpImageUrl,
                                    supportEntity.helpEntity.helpStartDateTime,
                                    supportEntity.helpEntity.helpEndDateTime,
                                    supportEntity.helpEntity.userEntity.addressName,
                                    supportEntity.helpEntity.userEntity.nickName,
                                )
                            ),
                            ExpressionUtils.`as`(
                                JPAExpressions.select(supportEntity.count())
                                    .from(supportEntity)
                                    .where(supportEntity.userEntity.id.eq(user.id)),
                                "supportCount"
                            ),
                        )
                    )
            )[0]
}
