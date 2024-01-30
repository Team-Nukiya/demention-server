package team.nukiya.demention.domain.point.domain

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import team.nukiya.demention.domain.support.exception.SupportNotFountException
import team.nukiya.demention.domain.support.repository.SupportEntityRepository
import team.nukiya.demention.domain.user.exception.UserNotFoundException
import team.nukiya.demention.domain.user.repository.UserEntityRepository
import team.nukiya.demention.global.mapper.GenericMapper

@Component
class PointMapper(
    private val userEntityRepository: UserEntityRepository,
    private val supportEntityRepository: SupportEntityRepository,
) : GenericMapper<Point, PointEntity> {
    override fun toDomain(entity: PointEntity): Point =
        Point(
            id = entity.id,
            giveUserId = entity.giveUserEntity!!.id,
            receiveSupportId = entity.receiveSupportEntity!!.id,
            point = entity.point,
            createdDateTime = entity.createdDateTime,
        )

    override fun toEntity(domain: Point): PointEntity {
        val giveUserEntity = userEntityRepository.findByIdOrNull(domain.giveUserId)
            ?: throw UserNotFoundException
        val receiveSupportEntity = supportEntityRepository.findByIdOrNull(domain.receiveSupportId)
            ?: throw SupportNotFountException

        return PointEntity(
            id = domain.id,
            giveUserEntity = giveUserEntity,
            receiveSupportEntity = receiveSupportEntity,
            point = domain.point,
        )
    }
}