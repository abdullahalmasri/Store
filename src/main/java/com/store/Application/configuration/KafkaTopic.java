package com.store.Application.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("tb-Msg-item").build();
    }

    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name("tb-Msg-items").build();
    }



}
