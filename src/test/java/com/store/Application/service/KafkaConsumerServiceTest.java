package com.store.Application.service;

import com.store.Application.common.data.ElasticData;
import com.store.Application.common.data.Item;
import com.store.Application.common.data.User;
import com.store.Application.common.data.UserId;
import com.store.Application.dao.model.Repository.UserRepository;
import com.store.Application.dao.model.Roles;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class KafkaConsumerServiceTest extends AbstractServicesTest {

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
    public void receive() throws Exception {
        Item item = new Item();
        item.setName("test-producer");
        item.setPrice(BigDecimal.valueOf(200).setScale(2, RoundingMode.HALF_EVEN));
        item.setUserId(userId.getId());
        Item savedItem = itemServices.saveItem(userId, item);
        Item foundItem = itemServices.findItemById(userId.getId(), savedItem.getId().getId());
        Assert.assertNotNull(foundItem);
        Assert.assertNotNull(foundItem.getId());
        Assert.assertNotNull(foundItem.getName());
        Assert.assertNotNull(foundItem.getPrice());
        ElasticData elasticData = ElasticData.builder().id(foundItem.getId().getId()).itemName(foundItem.getName()).price(foundItem.getPrice()).build();
        kafkaConsumerService.receive(elasticData);
        itemServices.removeByUserIdAndItemId(userId.getId(), foundItem.getId().getId());
    }
}