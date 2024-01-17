package team.nukiya.demention.domain.help.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import team.nukiya.demention.domain.help.domain.HelpMapper
import team.nukiya.demention.domain.help.repository.HelpEntityRepository
import java.util.UUID

@Transactional(readOnly = true)
@Component
class HelpReader(
    private val helpEntityRepository: HelpEntityRepository,
    private val helpMapper: HelpMapper,
) {
    fun getHelpById(helpId: UUID) =
        helpEntityRepository.findByIdOrNull(helpId)
            ?.let { helpMapper.toDomain(it) }
}
