package team.nukiya.demention.domain.support.domain

import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import team.nukiya.demention.domain.help.domain.HelpEntity
import team.nukiya.demention.domain.user.domain.UserEntity
import team.nukiya.demention.global.entity.BaseEntity
import java.util.UUID

@Table(name = "tbl_support")
@Entity
class SupportEntity(
    id: UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val userEntity: UserEntity?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "help_id", nullable = false)
    val helpEntity: HelpEntity?,

    @NotNull
    @Enumerated(STRING)
    val supportStatus: SupportStatus,
) : BaseEntity()
