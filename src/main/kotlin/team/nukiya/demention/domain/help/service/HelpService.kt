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

    fun createHelp(help: Help) {
        helpProcessor.saveHelp(help)
    }

    fun updateHelp(
        helpId: UUID,
        help: Help,
        userId: UUID,
    ) {
        helpReader.getHelpById(helpId)
            ?.apply { this.verifyIdentityVerification(userId = userId) }
            ?: throw HelpNotFoundException

        helpProcessor.saveHelp(help)
    }

    fun deleteHelp(helpId: UUID, userId: UUID) {
        helpReader.getHelpById(helpId)
            ?.apply { this.verifyIdentityVerification(userId = userId) }
            ?: throw HelpNotFoundException

        helpProcessor.deleteHelpById(helpId)
    }
}
