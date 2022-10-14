package com.store.Application.dao.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class BaseSqlEntity<I> implements BaseEntity<I> {
    @Id
    @Column(name = ModelEntity.ID, columnDefinition = "uuid")
    protected UUID id;

    @Column(name = ModelEntity.CREATED_TIME, updatable = false)
    protected long createdTime;

    @Override
    public UUID getUuid() {
        return id;
    }

    @Override
    public void setUuid(UUID id) {
        this.id = id;
    }

    @Override
    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        if (createdTime > 0) {
            this.createdTime = createdTime;
        }
    }
}
