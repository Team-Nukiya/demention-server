package team.nukiya.demention.domain.help.service

import org.springframework.stereotype.Service
import team.nukiya.demention.domain.help.domain.AllHelp
import team.nukiya.demention.domain.help.domain.Help
import team.nukiya.demention.domain.help.domain.HelpDetails
import team.nukiya.demention.domain.help.domain.HelpStatus
import team.nukiya.demention.domain.help.exception.HelpNotFoundException
import team.nukiya.demention.domain.user.domain.User
import team.nukiya.demention.domain.user.service.UserProcessor
import team.nukiya.demention.global.dto.Paging
import java.util.UUID

@Service
class HelpService(
    private val helpProcessor: HelpProcessor,
    private val helpReader: HelpReader,
    private val userProcessor: UserProcessor,
) {
    fun create(
        help: Help,
        currentUser: User,
        userName: String,
    ) {
        if (currentUser.name.isNullOrBlank()) {
            currentUser.updateName(name = userName)
            userProcessor.saveUser(currentUser)
        }

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
        currentUser: User,
        paging: Paging,
    ): List<AllHelp> =
        helpReader.getAllHelps(
            helpStatus = helpStatus,
            sido = currentUser.address.sido,
            paging = paging,
        )
}
