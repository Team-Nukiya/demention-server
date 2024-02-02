package team.nukiya.demention.domain.help.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import team.nukiya.demention.domain.user.domain.UserEntity
import team.nukiya.demention.global.entity.BaseEntity
import java.time.LocalDateTime
import java.util.UUID

@Table(name = "tbl_help")
@Entity
class HelpEntity(
    id: UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val userEntity: UserEntity?,

    @NotNull
    @Column(columnDefinition = "VARCHAR(15)")
    val title: String,

    @NotNull
    @Column(columnDefinition = "VARCHAR(300)")
    val content: String,

    @NotNull
    @Column(columnDefinition = "VARCHAR(15)")
    val compensation: String,

    @NotNull
    val helpImageUrl: String,

    @NotNull
    @Column(columnDefinition = "VARCHAR(7)")
    @Enumerated(STRING)
    val helpStatus: HelpStatus,

    @NotNull
    @Column(columnDefinition = "DATETIME(6)")
    val helpStartDateTime: LocalDateTime,

    @NotNull
    @Column(columnDefinition = "DATETIME(6)")
    val helpEndDateTime: LocalDateTime,
) : BaseEntity()
