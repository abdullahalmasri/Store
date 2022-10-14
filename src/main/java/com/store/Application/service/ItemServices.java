package com.store.Application.service;

import com.store.Application.common.data.Item;
import com.store.Application.common.data.UserId;

import java.util.List;
import java.util.UUID;

public interface ItemServices {

    Item findItemById(UUID userId, UUID itemId);

    List<Item> findAllItem();

    List<Item> findAllItemByUserId(UserId userId);

    Item findItemByName(String name);

    Item saveItem(UserId userId, Item item) throws Exception;

    void removeByUserIdAndItemId(UUID userId, UUID itemId);

}
