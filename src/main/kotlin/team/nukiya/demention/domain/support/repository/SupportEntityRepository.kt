package team.nukiya.demention.domain.support.repository

import org.springframework.data.repository.CrudRepository
import team.nukiya.demention.domain.support.domain.SupportEntity
import java.util.UUID

interface SupportEntityRepository : CrudRepository<SupportEntity, UUID>
