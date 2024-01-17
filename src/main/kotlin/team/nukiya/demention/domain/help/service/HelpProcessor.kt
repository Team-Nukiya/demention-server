package team.nukiya.demention.domain.help.service

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import team.nukiya.demention.domain.help.domain.Help
import team.nukiya.demention.domain.help.domain.HelpMapper
import team.nukiya.demention.domain.help.repository.HelpEntityRepository
import java.util.UUID

@Transactional
@Component
class HelpProcessor(
    private val helpEntityRepository: HelpEntityRepository,
    private val helpMapper: HelpMapper,
) {
    fun saveHelp(help: Help) =
        helpEntityRepository.save(
            helpMapper.toEntity(help)
        ).let { helpMapper.toDomain(it) }

    fun removeHelpById(helpId: UUID) {
        helpEntityRepository.deleteById(helpId)
    }
}
