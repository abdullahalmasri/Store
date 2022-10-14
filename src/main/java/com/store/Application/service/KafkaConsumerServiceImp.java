package com.store.Application.service;

import com.store.Application.common.data.ElasticData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerServiceImp implements KafkaConsumerService {

    @KafkaListener(
            topics = "${app.kafka.consumer.topic}")
    public void receive(@Payload ElasticData message) {
        log.info("the item is {} saved in Elastic ", message);
    }

}
