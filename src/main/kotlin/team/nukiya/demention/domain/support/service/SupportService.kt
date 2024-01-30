package team.nukiya.demention.domain.support.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.nukiya.demention.domain.help.domain.AllHelp
import team.nukiya.demention.domain.help.exception.HelpNotFoundException
import team.nukiya.demention.domain.help.service.HelpReader
import team.nukiya.demention.domain.support.domain.Support
import team.nukiya.demention.domain.support.domain.SupportStatus.Companion.RESUPPORT
import team.nukiya.demention.domain.support.domain.SupportStatus.CANCELED
import team.nukiya.demention.domain.support.exception.AlreadySupportException
import team.nukiya.demention.domain.support.exception.SupportNotFountException
import team.nukiya.demention.domain.support.repository.SupportRepository
import team.nukiya.demention.domain.user.domain.User
import team.nukiya.demention.global.dto.Paging
import java.util.UUID

@Transactional
@Service
class SupportService(
    private val supportRepository: SupportRepository,
    private val helpReader: HelpReader,
) {
    fun support(support: Support): UUID {
        if (supportRepository.existsByUserIdAndInStatus(support.userId, RESUPPORT)) {
            throw AlreadySupportException
        }

        helpReader.getHelpById(support.helpId)
            ?. apply { checkMine(support.userId) }
            ?: throw HelpNotFoundException

        return supportRepository.save(support).id
    }

    fun cancel(userId: UUID, helpId: UUID): UUID {
        val support = supportRepository.queryByUserIdAndHelpId(userId, helpId)
            ?.apply { checkCancellable() }
            ?: throw SupportNotFountException

        return supportRepository.save(
            support.copy(supportStatus = CANCELED)
        ).id
    }

    fun getHistories(user: User, paging: Paging): List<AllHelp> =
        supportRepository.getHistories(user, paging)
}
