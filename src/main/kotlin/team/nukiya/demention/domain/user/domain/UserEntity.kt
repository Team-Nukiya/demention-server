package team.nukiya.demention.domain.user.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import team.nukiya.demention.global.entity.BaseIdEntity
import java.util.UUID

@Table(name = "tbl_user")
@Entity
class UserEntity(

    override val id: UUID,

    @NotNull
    @Column(columnDefinition = "CHAR(11)")
    val phoneNumber: String,

    @NotNull
    @Column(columnDefinition = "VARCHAR(10)")
    val nickName: String,

    @Column(columnDefinition = "VARCHAR(10)")
    val name: String?,

    @NotNull
    @Column(columnDefinition = "VARCHAR(20)")
    val area: String,

    @NotNull
    @Column(columnDefinition = "VARCHAR(20)")
    @Enumerated(STRING)
    val authority: Authority,

    isDeleted: Boolean,
) : BaseIdEntity() {

    @NotNull
    @Column(columnDefinition = "BIT(1)")
    var isDeleted = isDeleted
        protected set
}