package shopping.order.repository.entity;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

@MappedSuperclass
public abstract class TimeBaseEntity {

    @Column(name = "created_at", columnDefinition = "TIMESTAMP(6)", nullable = false, updatable = false)
    protected Instant createdAt;

    @PrePersist
    void prePersist() {
        Instant now = Instant.now();

        createdAt = createdAt != null ? createdAt : now;
    }
}
