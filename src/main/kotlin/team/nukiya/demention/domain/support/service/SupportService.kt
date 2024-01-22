package team.nukiya.demention.domain.support.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.nukiya.demention.domain.support.domain.Support
import team.nukiya.demention.domain.support.domain.SupportStatus.Companion.RESUPPORT
import team.nukiya.demention.domain.support.domain.SupportStatus.UNSUPPORTING
import team.nukiya.demention.domain.support.exception.AlreadySupportException
import team.nukiya.demention.domain.support.exception.SupportNotFountException
import team.nukiya.demention.domain.support.repository.SupportRepository
import java.util.UUID

@Transactional
@Service
class SupportService(
    private val supportRepository: SupportRepository,
) {
    fun support(support: Support) {
        if (supportRepository.existsByUserIdAndInStatus(support.userId, RESUPPORT)) {
            throw AlreadySupportException
        }

        supportRepository.save(support)
    }

    fun unSupport(userId: UUID, helpId: UUID) {
        val support = supportRepository.queryByUserIdAndHelpId(userId, helpId)
            ?.apply { checkCancellable() }
            ?: throw SupportNotFountException

        supportRepository.save(
            support.copy(supportStatus = UNSUPPORTING)
        )
    }
}
