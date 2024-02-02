package team.nukiya.demention.domain.point.repository

import org.springframework.data.repository.CrudRepository
import team.nukiya.demention.domain.point.domain.PointEntity
import java.util.UUID

interface PointEntityRepository : CrudRepository<PointEntity, UUID>
