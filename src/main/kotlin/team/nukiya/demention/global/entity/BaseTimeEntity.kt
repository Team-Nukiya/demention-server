package team.nukiya.demention.global.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity {
    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME(6)")
    @CreatedDate
    var createdDateTime: LocalDateTime = LocalDateTime.MIN
        protected set

    @Column(nullable = false, columnDefinition = "DATETIME(6)")
    @LastModifiedDate
    var modifiedDateTime: LocalDateTime = LocalDateTime.MIN
        protected set
}
