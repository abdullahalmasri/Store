package com.store.Application.service;

import com.store.Application.common.data.ElasticData;
import com.store.Application.common.data.Item;
import com.store.Application.common.data.UserId;
import com.store.Application.dao.model.Repository.ItemRepository;
import com.store.Application.dao.model.Repository.elk.ElasticSearchComponent;
import com.store.Application.dao.model.sql.ItemEntity;
import com.store.Application.validation.ValidationEntity;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("itemServices")
@Slf4j
public class ItemServicesImp extends ValidationEntity implements ItemServices {

    private final ItemRepository itemRepository;
    private final ElasticSearchComponent elasticSearchService;

    private final ModelMapper modelMapper;


    public ItemServicesImp(ItemRepository itemRepository,
                           ElasticSearchComponent elasticSearchService,
                           ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.elasticSearchService = elasticSearchService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Item findItemById(UUID userId, UUID itemId) {
        boolean check = itemRepository.existsById(itemId);
        Item item = null;
        if (check)
            item = (itemRepository.findItemEntitiesByUserIdAndId(userId, itemId)).toData();
        return item;
    }

    @Override
    public List<Item> findAllItem() {
        return itemRepository.findAll().stream().map(ItemEntity::toData).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<Item> findAllItemByUserId(UserId userId) {
        List<Item> items = itemRepository.findAllByUserId(userId.getId()).parallelStream()
                .map(element -> modelMapper.map(element, Item.class))
                .collect(Collectors.toList());
        return items;
    }

    @Override
    public Item findItemByName(String name) {
        return (itemRepository.findItemEntitiesByName(name)).toData();
    }

    @Override
    public Item saveItem(UserId userId, Item item) throws Exception {
        item.setUserId(userId.getId());
        if (item.getId() == null) {
            item = validationItemImp(item, Item.class);
        }
        ElasticData elasticData = ElasticData.builder().id(item.getId().getId()).itemName(item.getName()).price(item.getPrice()).build();
        elasticSearchService.save(elasticData);
        ItemEntity itemEntity = modelMapper.map(item, ItemEntity.class);
        itemRepository.save(itemEntity);
        return item;
    }

    @Transactional
    @Override
    public void removeByUserIdAndItemId(UUID userId, UUID itemId) {
        boolean check = itemRepository.existsById(itemId);
        if (check) {
            if (elasticSearchService.existsById(itemId))
                elasticSearchService.deleteById(itemId);
            itemRepository.removeItemEntityByUserIdAndId(userId, itemId);
        }
    }
}
