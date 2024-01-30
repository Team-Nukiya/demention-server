package team.nukiya.demention.domain.user.service

import com.querydsl.core.types.ExpressionUtils
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import team.nukiya.demention.domain.help.domain.QHelpEntity.helpEntity
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
            .select(
                QQueryUserInformation(
                    Expressions.constantAs(user.id, userEntity.id),
                    Expressions.asString(user.nickName).`as`(userEntity.nickName),
                    Expressions.asString(user.address.addressName).`as`(userEntity.addressName),
                    ExpressionUtils.`as`(
                        JPAExpressions.select(helpEntity.count())
                            .from(helpEntity)
                            .where(helpEntity.userEntity.id.eq(user.id)),
                        "helpCount"
                    ),
                    ExpressionUtils.`as`(
                        JPAExpressions.select(supportEntity.count())
                            .from(supportEntity)
                            .where(supportEntity.userEntity.id.eq(user.id)),
                        "supportCount"
                    ),
                )
            )
            .from(userEntity)
            .where(userEntity.id.eq(user.id))
            .fetchOne()
}
