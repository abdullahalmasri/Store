package com.store.Application.dao.model;

import java.util.UUID;

public interface BaseEntity<I> extends ToData<I> {

    UUID getUuid();

    void setUuid(UUID id);

    long getCreatedTime();

    void setCreatedTime(long createdTime);
}
