package com.store.Application.service;

import com.store.Application.common.data.Item;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducerServiceImp implements KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, Item> kafkaTemplate;

    @Value("${app.kafka.producer.topic}")
    private String topic;

    @Override
    public void send(Item message) {
        log.info("message sent: {}", message);
        kafkaTemplate.send(topic, message);
    }
}
