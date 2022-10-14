package com.store.Application.service;

import com.store.Application.common.data.ElasticData;

public interface KafkaConsumerService {
    void receive(ElasticData message);
}
