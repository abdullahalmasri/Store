package com.store.Application.service;

import com.store.Application.common.data.User;
import com.store.Application.common.data.UserId;
import com.store.Application.dao.model.Repository.UserRepository;
import com.store.Application.dao.model.Roles;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
public class BaseUserServicesTest extends AbstractServicesTest {
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
    public void getAllUser() throws Exception {
        User user2 = userServices.findUserById(UUID.fromString("e2d54e03-fdc1-456a-a4a4-051ba0e6fe49"));
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            User user = new User();
            user.setUserName("test10" + i);
            user.setPassword("123");
            user.setEmail("email-test10" + i + "@test.com");
            user.setRoles(Roles.ADMIN);
            user.setCreatedTime(System.currentTimeMillis());
            user.setUserId(user2.getId().getId());
            userServices.saveUser(user2.getId(), user);
            users.add(user);
        }
        List<User> foundUsers = userServices.getAllUser(user2.getId().getId());
        for (int i = 0; i < 2; i++) {
            Assert.assertNotNull(foundUsers.get(i));
            Assert.assertEquals(foundUsers.get(i), users.get(i));
        }
        for (int i = 0; i < 2; i++) {
            userRepository.deleteById(foundUsers.get(i).getId().getId());
        }

        log.info("Successfully Tested !! ");

    }

    @Test
    public void saveUser() throws Exception {
        User user2 = userServices.findUserById(UUID.fromString("e2d54e03-fdc1-456a-a4a4-051ba0e6fe49"));
        User user = new User();
        user.setUserName("test12");
        user.setPassword("123");
        user.setEmail("email11@test.com");
        user.setRoles(Roles.ADMIN);
        User savedUser = userServices.saveUser(user2.getId(), user);
        User foundUser = userServices.findUserById(savedUser.getId().getId());
        Assert.assertNotNull(foundUser);
        Assert.assertEquals(savedUser, foundUser);
        userRepository.deleteById(foundUser.getId().getId());
    }

    @Test
    public void findUserById() throws Exception {
        User user2 = userServices.findUserById(UUID.fromString("e2d54e03-fdc1-456a-a4a4-051ba0e6fe49"));

        Assert.assertNotNull(user2);

    }
}
