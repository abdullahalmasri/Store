package com.store.Application.service;


import com.store.Application.dao.model.Repository.elk.ElasticSearchComponent;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@Slf4j
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractServicesTest {
    @Autowired
    protected UserServices userServices;
    @Autowired
    protected ItemServices itemServices;

    @Autowired
    protected KafkaConsumerService kafkaConsumerService;

    @Autowired
    protected KafkaProducerService kafkaProducerService;

    @Autowired
    protected ElasticSearchComponent elasticSearchComponent;


}
