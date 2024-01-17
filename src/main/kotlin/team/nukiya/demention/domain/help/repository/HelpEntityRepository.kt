package team.nukiya.demention.domain.help.repository

import org.springframework.data.repository.CrudRepository
import team.nukiya.demention.domain.help.domain.HelpEntity
import java.util.UUID

interface HelpEntityRepository : CrudRepository<HelpEntity, UUID>
