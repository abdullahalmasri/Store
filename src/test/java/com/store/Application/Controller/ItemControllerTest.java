package com.store.Application.Controller;

import com.store.Application.common.data.Item;
import com.store.Application.common.data.ItemId;
import com.store.Application.common.data.User;
import com.store.Application.common.data.UserId;
import com.store.Application.dao.model.Repository.UserRepository;
import com.store.Application.dao.model.Roles;
import com.store.Application.service.ItemServices;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Slf4j
public class ItemControllerTest extends AbstractControllerTest {

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    private UserServices userServices;
    @Autowired
    private ItemServices itemServices;

    static UserId userId = new UserId(UUID.randomUUID());

    @Before
    public void beforeAll() throws Exception {
        User adminUser = userServices.findUserById(UUID.fromString("e2d54e03-fdc1-456a-a4a4-051ba0e6fe49"));
        User savedUser = new User();
        savedUser.setUserName("test-controller");
        savedUser.setPassword("123");
        savedUser.setEmail("controller-test@test.com");
        savedUser.setRoles(Roles.ADMIN);
        savedUser.setId(userId);
        savedUser.setUserId(adminUser.getId().getId());
        ResultActions testUser =
                mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userController.getToken(adminUser))
                        .content(Objects.requireNonNull(objectToJson(savedUser))));
//                .andExpect(status().isOk());
        userServices.saveUser(adminUser.getId(), savedUser);
        log.info("the test user saved successfully with {}", testUser);
    }

    @After
    public void afterAll() {
        // here should be test for delete API i will leave it for you don't forget to develop an API for remove in UserController it should be easy
        // since i will test the item (remove API) so should be similar but diff in path of API
        // just in the moment i will use direct the repository to delete the new user
        // be careful cuz here you really need the api
        userRepository.deleteById(userId.getId());
    }

    @Test
    public void getItemById() throws Exception {
        User user = userServices.findUserById(userId.getId());
        Item item = new Item();
        item.setUserId(userId.getId());
        item.setName("test-item-controller");
        item.setPrice(BigDecimal.valueOf(200).setScale(2, RoundingMode.HALF_EVEN));
        item.setId(new ItemId(UUID.randomUUID()));
        item.setCreatedTime(System.currentTimeMillis());
        itemServices.saveItem(user.getId(), item);
        log.info("the item is {}", item);
        log.info("the user id is {}", userId);
        ResultActions foundItem = mockMvc.perform(
                get("/api/item/{userId}/{itemId}", userId, item.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userController.getToken(user))
        )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void saveItem() throws Exception {
        User user = userServices.findUserById(userId.getId());
        Item item = new Item();
        item.setUserId(userId.getId());
        item.setName("test-item-controller");
        item.setPrice(BigDecimal.valueOf(200)
                .setScale(2, RoundingMode.HALF_EVEN));
        ResultActions savedItem =
                mockMvc.perform(post("/api/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "
                                + userController.getToken(user))

                        .content(Objects.requireNonNull(objectToJson(item))))
                        .andExpect(status().isOk());

        log.info("the test item saved successfully with {}", savedItem);

    }

    @Test
    public void removeItem() throws Exception {
        User user = userServices.findUserById(userId.getId());
        Item savedItem = new Item();
        savedItem.setPrice(BigDecimal.valueOf(200).setScale(2, RoundingMode.HALF_EVEN));
        savedItem.setName("test-doll");
        savedItem.setUserId(user.getId().getId());
        Item item = itemServices.saveItem(userId, savedItem);
        Thread.sleep(1000);

        Item foundItem = itemServices.findItemById(user.getId().getId(), item.getId().getId());
        ResultActions deletedItem =
                mockMvc.perform(MockMvcRequestBuilders.delete("/api/item/{itemId}", foundItem.getId().toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userController.getToken(user)))
                        .andExpect(status().isOk());
        log.info("the item deleted successfully with id {}", deletedItem);
    }
}