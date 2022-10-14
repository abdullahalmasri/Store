package com.store.Application.service;

import com.store.Application.common.data.Item;
import com.store.Application.common.data.User;
import com.store.Application.common.data.UserId;
import com.store.Application.dao.model.Repository.ItemRepository;
import com.store.Application.dao.model.Repository.UserRepository;
import com.store.Application.dao.model.Roles;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EnableAutoConfiguration
public class BaseItemServicesTest extends AbstractServicesTest {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;

    static UserId userId = new UserId(UUID.randomUUID());


    @Before
    public void before() throws Exception {
        User adminUser = userServices.findUserById(UUID.fromString("e2d54e03-fdc1-456a-a4a4-051ba0e6fe49"));
        User user = new User();
        user.setId(userId);
        user.setUserName("test100");
        user.setPassword("123");
        user.setEmail("email@test.com");
        user.setRoles(Roles.ADMIN);
        user.setCreatedTime(System.currentTimeMillis());
        userServices.saveUser(adminUser.getId(), user);
    }

    @After
    public void after() {
        userRepository.deleteById(userId.getId());
    }

    @Test
    public void findItemById() throws Exception {
        Item item = new Item();
        item.setUserId(userId.getId());
        item.setName("Item-Test-101");
        item.setPrice(BigDecimal.valueOf(200_000).setScale(2, RoundingMode.HALF_EVEN));
        Item savedItem = itemServices.saveItem(userId, item);
        Item foundItem = itemServices.findItemById(userId.getId(), savedItem.getId().getId());
        Assert.assertNotNull(foundItem);
        Assert.assertEquals(savedItem, foundItem);
        itemServices.removeByUserIdAndItemId(userId.getId(), foundItem.getId().getId());

    }

    @Test
    public void findAllItem() throws Exception {
        List<Item> savedItems = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Item item = new Item();
            item.setUserId(userId.getId());
            item.setName("Item-Test-10" + i);
            item.setPrice(BigDecimal.valueOf(200_000 + i).setScale(2, RoundingMode.HALF_EVEN));
            itemServices.saveItem(userId, item);
            savedItems.add(item);
        }
        List<Item> itemList = itemServices.findAllItem();
        for (int i = 0; i < itemList.size(); i++) {
            Assert.assertNotNull(itemList.get(i));
            Assert.assertEquals(itemList.get(i), savedItems.get(i));
        }
        itemList.forEach(item -> itemServices.removeByUserIdAndItemId(userId.getId(), item.getId().getId()));
    }


    @Test
    public void findAllItemByItemIdAndUserId() throws Exception {

        List<Item> savedItems = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Item item = new Item();
            item.setUserId(userId.getId());
            item.setName("Item-Test-10" + i);
            item.setPrice(BigDecimal.valueOf(2000 + i).setScale(2, RoundingMode.HALF_EVEN));
            itemServices.saveItem(userId, item);
            savedItems.add(item);
        }
        List<Item> itemList = itemServices.findAllItemByUserId(userId);
        for (int i = 0; i < itemList.size(); i++) {
            Assert.assertNotNull(itemList.get(i));
            Assert.assertEquals(itemList.get(i).getName(), savedItems.get(i).getName());
            Assert.assertEquals(itemList.get(i).getPrice(), savedItems.get(i).getPrice());
            Assert.assertEquals(itemList.get(i).getUserId(), savedItems.get(i).getUserId());
        }
        savedItems.forEach(item -> itemServices.removeByUserIdAndItemId(userId.getId(), item.getId().getId()));
    }

    @Test
    public void findItemByName() throws Exception {
        Item item = new Item();
        item.setUserId(userId.getId());
        item.setName("Item-Test-101");
        item.setPrice(BigDecimal.valueOf(2000).setScale(2, RoundingMode.HALF_EVEN));
        Item savedItem = itemServices.saveItem(userId, item);
        Item foundItem = itemServices.findItemByName(savedItem.getName());
        Assert.assertNotNull(foundItem);
        Assert.assertEquals(savedItem, foundItem);
        itemServices.removeByUserIdAndItemId(userId.getId(), foundItem.getId().getId());
    }

    @Test
    public void saveItem() throws Exception {
        Item item = new Item();
        item.setUserId(userId.getId());
        item.setName("Item-Test-101");
        item.setPrice(BigDecimal.valueOf(2000).setScale(2, RoundingMode.HALF_EVEN));
        Item savedItem = itemServices.saveItem(userId, item);
        Item foundItem = itemServices.findItemById(userId.getId(), savedItem.getId().getId());
        Assert.assertNotNull(foundItem);
        Assert.assertEquals(savedItem, foundItem);
        itemServices.removeByUserIdAndItemId(userId.getId(), foundItem.getId().getId());
    }

    @Test
    public void removeByUserIdAndItemId() throws Exception {
        Item item = new Item();
        item.setUserId(userId.getId());
        item.setName("Item-Test-101");
        item.setPrice(BigDecimal.valueOf(200_000).setScale(2, RoundingMode.HALF_EVEN));
        Item savedItem = itemServices.saveItem(userId, item);
        Item foundItem = itemServices.findItemByName(savedItem.getName());
        Assert.assertNotNull(foundItem);
        Assert.assertEquals(savedItem, foundItem);
        itemServices.removeByUserIdAndItemId(userId.getId(), foundItem.getId().getId());
    }
}