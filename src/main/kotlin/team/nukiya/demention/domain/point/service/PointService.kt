package team.nukiya.demention.domain.point.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.nukiya.demention.domain.point.domain.Point
import team.nukiya.demention.domain.point.repository.PointRepository
import team.nukiya.demention.domain.support.exception.SupportNotFountException
import team.nukiya.demention.domain.support.repository.SupportRepository
import java.util.UUID

@Transactional(readOnly = true)
@Service
class PointService(
    private val supportRepository: SupportRepository,
    private val pointRepository: PointRepository,
) {
    @Transactional
    fun give(point: Point): UUID {
        supportRepository.queryById(point.receiveSupportId)
            ?.apply { checkIsDone() }
            ?: throw SupportNotFountException

        return pointRepository.save(point).id
    }
}