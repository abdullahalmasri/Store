package com.store.Application.dao.model.Repository.elk;

import com.store.Application.common.data.ElasticData;
import com.store.Application.common.data.User;
import com.store.Application.common.data.UserId;
import com.store.Application.dao.model.Repository.UserRepository;
import com.store.Application.dao.model.Roles;
import com.store.Application.service.AbstractServicesTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class ElasticSearchComponentTest extends AbstractServicesTest {

    @Autowired
    private UserRepository userRepository;


    static UserId userId = new UserId(UUID.randomUUID());


    @Before
    public void before() throws Exception {
        User user2 = userServices.findUserById(UUID.fromString("e2d54e03-fdc1-456a-a4a4-051ba0e6fe49"));
        User user = new User();
        user.setId(userId);
        user.setUserName("test100");
        user.setPassword("123");
        user.setEmail("email@test.com");
        user.setRoles(Roles.ADMIN);
        user.setCreatedTime(System.currentTimeMillis());
        userServices.saveUser(user2.getId(), user);
    }

    @After
    public void after() {
        userRepository.deleteById(userId.getId());
    }


    @Test
    public void save() throws InterruptedException {
        // since we are testing the data of Elasticsearch it's good practice to get from Db but there is examples in kafkaConsumerTest shows the way get data from Db then send to Elasticsearch
        ElasticData elasticData = ElasticData.builder().id(UUID.randomUUID()).itemName("test-unit").price(BigDecimal.valueOf(200).setScale(1, RoundingMode.HALF_EVEN)).build();
        Assert.assertNotNull(elasticData);
        ElasticData savedData = elasticSearchComponent.save(elasticData);
        // here we add thread sleep cuz if you remember to connection time out need 5000 ms so we wait actually until data indexed in elasticsearch
        Thread.sleep(6000);

        Optional<ElasticData> foundData = elasticSearchComponent.findById(savedData.getId());
        log.info("option elasticData {}", foundData.isPresent());
        Assert.assertTrue(foundData.isPresent());
//        Assert.assertEquals(savedData, foundData);
        elasticSearchComponent.deleteById(foundData.get().getId());
    }

    @Test
    public void saveAll() throws InterruptedException {
        List<ElasticData> dataList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            ElasticData elasticData = ElasticData.builder().id(UUID.randomUUID()).itemName("test-unit-" + i).price(BigDecimal.valueOf(200 + i).setScale(1, RoundingMode.HALF_EVEN)).build();
            Assert.assertNotNull(elasticData);
            ElasticData savedData = elasticSearchComponent.save(elasticData);
            dataList.add(savedData);
        }
        Thread.sleep(6000);

        List<ElasticData> foundData = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            ElasticData elasticData = elasticSearchComponent.findById(dataList.get(i).getId()).orElseGet(() -> ElasticData.builder().build());
            Assert.assertNotNull(elasticData);
            foundData.add(elasticData);
        }
        for (int i = 0; i < foundData.size(); i++) {
            Assert.assertEquals(dataList.get(i), foundData.get(i));
        }
        foundData.forEach(elasticData -> elasticSearchComponent.delete(elasticData));
    }

    @Test
    public void findById() throws InterruptedException {
        ElasticData elasticData = ElasticData.builder().id(UUID.randomUUID()).itemName("test-unit").price(BigDecimal.valueOf(200).setScale(1, RoundingMode.HALF_EVEN)).build();
        Assert.assertNotNull(elasticData);
        ElasticData savedData = elasticSearchComponent.save(elasticData);
        Thread.sleep(6000);
        ElasticData foundData = elasticSearchComponent.findById(savedData.getId()).orElseGet(() -> ElasticData.builder().build());
        Assert.assertNotNull(foundData);
        Assert.assertEquals(savedData, foundData);
        elasticSearchComponent.deleteById(foundData.getId());
    }

    @Test
    public void existsById() throws InterruptedException {
        ElasticData elasticData = ElasticData.builder().id(UUID.randomUUID()).itemName("test-unit").price(BigDecimal.valueOf(200).setScale(1, RoundingMode.HALF_EVEN)).build();
        Assert.assertNotNull(elasticData);
        ElasticData savedData = elasticSearchComponent.save(elasticData);
        Thread.sleep(6000);
        Optional<ElasticData> foundData = Optional.of(elasticSearchComponent.findById(savedData.getId())).get();
        Assert.assertTrue(foundData.isPresent());
        elasticSearchComponent.deleteById(foundData.get().getId());
    }

    @Test
    public void findAll() {
        //same as save all in the end we test services that mean the data that we gonna send
        // i will leave it for you in case it could be practice
    }

    @Test
    public void findAllById() {
        // it will be as same as find by id since the elasticsearch generate new uuid for each index and document
    }

    @Test
    public void count() {
        // i  lift it in services since i use bucket , it should be easy to
        // develop it and try test it
    }

    @Test
    public void deleteById() {
        // i know many comment but i have already use it above
    }

    @Test
    public void delete() {
        // ok that seems also interesting but i have use it above in method saveAll()
        // in the end most of them get be used so i guess i need to leave it
        // either to practice or get comments
    }

    //TODO
    @Test
    public void deleteAllById() {
    }

    //TODO
    @Test
    public void deleteAll() {
    }


}