package com.store.Application.dao.model.Repository;

import com.store.Application.dao.model.sql.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<ItemEntity, UUID> {


    /**
     * Find item info by id.
     *
     * @param userId the user id
     * @param itemId the item id
     * @return the item info object
     */
    ItemEntity findItemEntitiesByUserIdAndId(
            UUID userId,
            UUID itemId
    );


    /**
     * Find items info by userId.
     *
     * @param userId the user id
     * @return the item info object
     */
    @Query("SELECT i " +
            "FROM ItemEntity i " +
            "WHERE i.userId = :userId")
    List<ItemEntity> findAllByUserId(UUID userId);

    /**
     * Find items info.
     *
     * @return the items info object
     */
    List<ItemEntity> findAll();

    /**
     * Find item info by name.
     *
     * @param name the item name
     * @return the item info object
     */
    ItemEntity findItemEntitiesByName(String name);

    /**
     * Delete item by userId and itemId.
     *
     * @param userId the user id
     * @param itemId the item id
     */
    void removeItemEntityByUserIdAndId(UUID userId, UUID itemId);

}
