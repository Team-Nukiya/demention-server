package team.nukiya.demention.domain.help.service

import org.springframework.stereotype.Service
import team.nukiya.demention.domain.help.domain.AllHelp
import team.nukiya.demention.domain.help.domain.Help
import team.nukiya.demention.domain.help.domain.HelpDetails
import team.nukiya.demention.domain.help.domain.HelpStatus
import team.nukiya.demention.domain.help.exception.HelpNotFoundException
import team.nukiya.demention.domain.user.domain.User
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

    fun getDetails(helpId: UUID): HelpDetails =
        helpReader.getHelpDetailsById(helpId = helpId) ?: throw HelpNotFoundException

    fun getAll(
        helpStatus: HelpStatus,
        page: Long,
        limit: Long,
        currentUser: User,
    ): List<AllHelp> =
        helpReader.getAllHelps(
            helpStatus = helpStatus,
            sido = currentUser.address.sido,
            page = page,
            limit = limit,
        )
}
