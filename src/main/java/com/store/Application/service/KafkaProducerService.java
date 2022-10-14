package com.store.Application.service;

import com.store.Application.common.data.Item;

public interface KafkaProducerService {
    void send(Item message);
}
