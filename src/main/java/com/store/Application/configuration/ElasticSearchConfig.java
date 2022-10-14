package com.store.Application.configuration;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Slf4j
@EnableJpaRepositories(basePackages = {"com.store.Application.dao.model.Repository"})
@EnableElasticsearchRepositories(basePackages = "com.store.Application.dao.model.Repository")
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

        @Value("${spring.elasticsearch.uris}")
        private String uri;

    //    @Value("${spring.elasticsearch.name}")
    //    private String elasticsearchName;

        @Value("${spring.elasticsearch.connection-timeout}")
        private long connectionTimeout;

        @Value("${spring.elasticsearch.socket-timeout}")
        private long socketTimeout;

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {

        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(uri).withConnectTimeout(connectionTimeout)
                .withSocketTimeout(socketTimeout)
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    public ElasticsearchOperations elasticsearch() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }


}
