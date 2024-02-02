package team.nukiya.demention.domain.user.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.SQLRestriction
import team.nukiya.demention.global.entity.BaseUUIDEntity
import java.time.LocalDateTime
import java.util.UUID

@SQLRestriction("deleted_date_time is null")
@Table(name = "tbl_user")
@Entity
class UserEntity(
    id: UUID,

    @NotNull
    @Column(columnDefinition = "CHAR(11)")
    val phoneNumber: String,

    @NotNull
    @Column(columnDefinition = "VARCHAR(10)")
    val nickName: String,

    @NotNull
    @Column(columnDefinition = "VARCHAR(500)")
    val addressName: String,

    @NotNull
    @Column(columnDefinition = "VARCHAR(40)")
    val sido: String,

    @NotNull
    @Column(columnDefinition = "VARCHAR(40)")
    val gungu: String,

    @NotNull
    @Column(columnDefinition = "VARCHAR(40)")
    val eupMyeonDong: String,

    @NotNull
    @Column(columnDefinition = "VARCHAR(20)")
    @Enumerated(STRING)
    val authority: Authority,

    @Column(columnDefinition = "DATETIME(6)")
    val deletedDateTime: LocalDateTime? = null,
) : BaseUUIDEntity()
