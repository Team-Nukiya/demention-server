package team.nukiya.demention.domain.point.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import team.nukiya.demention.domain.support.domain.SupportEntity
import team.nukiya.demention.domain.user.domain.UserEntity
import team.nukiya.demention.global.entity.BaseEntity
import java.util.UUID

@Table(name = "tbl_point")
@Entity
class PointEntity(
    id: UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "give_user_id", nullable = false)
    val giveUserEntity: UserEntity?,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receive_support_id", nullable = false)
    val receiveSupportEntity: SupportEntity?,

    @NotNull
    @Column(columnDefinition = "DECIMAL(2, 1)")
    val point: Float,
) : BaseEntity()