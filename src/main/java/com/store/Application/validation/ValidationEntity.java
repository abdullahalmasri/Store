package com.store.Application.validation;

import com.store.Application.common.data.Item;
import com.store.Application.common.data.ItemId;
import com.store.Application.common.data.User;
import com.store.Application.common.data.UserId;
import com.store.Application.service.KafkaProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Slf4j
public abstract class ValidationEntity {
    @Autowired
    private KafkaProducerService kafkaProducerService;

    protected User validationTypeImp(User entity, Class responseClass) throws Exception {

        log.info("the class from Type {}", responseClass.getClass());
        try {

            if (entity.getId() == null) {
                UserId newUserId = new UserId(UUID.randomUUID());
                entity.setId(newUserId);
                entity.setCreatedTime(System.currentTimeMillis());
            }

            return entity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    protected Item validationItemImp(Item entity, Class responseClass) throws Exception {

        log.info("the class from Type {}", responseClass.getClass());
        try {

            if (entity.getId() == null) {
                ItemId newUserId = new ItemId(UUID.randomUUID());
                entity.setId(newUserId);
                entity.setCreatedTime(System.currentTimeMillis());
            }
            kafkaProducerService.send(entity);
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
