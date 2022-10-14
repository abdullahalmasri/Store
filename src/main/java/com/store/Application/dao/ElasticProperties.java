package com.store.Application.dao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticProperties {
    private String name;
    private String uri;
    private Integer connectionTimeoutMs;
    private Integer socketTimeoutMs;
}
