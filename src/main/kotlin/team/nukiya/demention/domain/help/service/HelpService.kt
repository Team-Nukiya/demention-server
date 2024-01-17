package team.nukiya.demention.domain.help.service

import org.springframework.stereotype.Service
import team.nukiya.demention.domain.help.domain.Help
import team.nukiya.demention.domain.help.exception.HelpNotFoundException
import java.util.UUID

@Service
class HelpService(
    private val helpProcessor: HelpProcessor,
    private val helpReader: HelpReader,
) {
    fun create(help: Help) {
        helpProcessor.saveHelp(help)
    }

    fun modify(
        helpId: UUID,
        help: Help,
        userId: UUID,
    ) {
        helpReader.getHelpById(helpId)
            ?.apply { verifyIdentityVerification(userId = userId) }
            ?: throw HelpNotFoundException

        helpProcessor.saveHelp(help)
    }

    fun remove(helpId: UUID, userId: UUID) {
        helpReader.getHelpById(helpId)
            ?.apply { verifyIdentityVerification(userId = userId) }
            ?: throw HelpNotFoundException

        helpProcessor.removeHelpById(helpId)
    }
}
