package team.nukiya.demention.domain.point.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import team.nukiya.demention.domain.point.domain.Point
import team.nukiya.demention.domain.point.domain.PointMapper

@Repository
class PointRepository(
    private val pointEntityRepository: PointEntityRepository,
    private val pointMapper: PointMapper,
    private val jpaQueryFactory: JPAQueryFactory,
) {
    fun save(point: Point): Point =
        pointEntityRepository.save(
            pointMapper.toEntity(point)
        ).let { pointMapper.toDomain(it) }
}