package team.nukiya.demention.domain.support.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.nukiya.demention.domain.support.domain.Support
import team.nukiya.demention.domain.support.domain.SupportStatus.Companion.RESUPPORT
import team.nukiya.demention.domain.support.domain.SupportStatus.CANCELED
import team.nukiya.demention.domain.support.exception.AlreadySupportException
import team.nukiya.demention.domain.support.exception.SupportNotFountException
import team.nukiya.demention.domain.support.repository.SupportRepository
import java.util.UUID

@Transactional
@Service
class SupportService(
    private val supportRepository: SupportRepository,
) {
    fun support(support: Support): UUID {
        if (supportRepository.existsByUserIdAndInStatus(support.userId, RESUPPORT)) {
            throw AlreadySupportException
        }

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
}
