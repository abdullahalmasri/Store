package com.store.Application.Controller;


import com.store.Application.common.data.User;
import com.store.Application.common.data.UserId;
import com.store.Application.dao.model.Repository.UserRepository;
import com.store.Application.dao.model.Roles;
import com.store.Application.service.UserServices;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Objects;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class UserControllerTest extends AbstractControllerTest {
    @Autowired
    private UserServices userServices;

    @Autowired
    private UserRepository userRepository;

    static UserId userId = new UserId(UUID.randomUUID());


    @Before
    public void beforeTest() throws Exception {
        User adminUser = userServices.findUserById(
                UUID.fromString("e2d54e03-fdc1-456a-a4a4-051ba0e6fe49"));
        User savedUser = new User();
        savedUser.setUserName("test-controller");
        savedUser.setPassword("123");
        savedUser.setEmail("controller-test@test.com");
        savedUser.setRoles(Roles.ADMIN);
        savedUser.setUserId(adminUser.getId().getId());
        savedUser.setId(userId);
        ResultActions testUser =
                mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(objectToJson(savedUser))));
//                .andExpect(status().isOk());
        userServices.saveUser(adminUser.getId(), savedUser);
        log.info("the test user saved successfully with {}", testUser);
    }

    @After
    public void afterTest() {
        // here should be test for delete API i will leave it for you don't forget to develop an API for remove in UserController it should be easy
        // since i will test the item (remove API) which found in ItemControllerTest so should be similar but diff in path of API
        // as mentioned in class ItemControllerTest
        userRepository.deleteById(userId.getId());
    }

    @Test
    public void welcome() {
        // you can do get method , i didn't know what to do in that method in reality so it's your choice
    }

    @Test
    public void saveUser() throws Exception {
        User user = userServices.findUserById(userId.getId());
        ResultActions testUser =
                mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userController.getToken(user))
                        .content(Objects.requireNonNull(objectToJson(user))))
                        .andExpect(status().isOk());
        log.info("the test user saved successfully with {}", testUser);

    }

    @Test
    public void getToken() throws Exception {
        User user = userServices.findUserById(UUID.fromString("e2d54e03-fdc1-456a-a4a4-051ba0e6fe49"));
        ResultActions getToken =
                mockMvc.perform(post("/api/getToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(objectToJson(user))))
                        .andExpect(status().isOk());
        log.info("the test user get token successfully with {}", getToken);
    }

    @Test
    public void logout() throws Exception {
        User user = userServices.findUserById(UUID.fromString("e2d54e03-fdc1-456a-a4a4-051ba0e6fe49"));
        ResultActions logout =
                mockMvc.perform(post("/api/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userController.getToken(user))
                )
                        .andExpect(status().isOk());
        log.info("the test user logout successfully with {}", logout);
    }


}