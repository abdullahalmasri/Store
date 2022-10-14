package com.store.Application.common.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@NoArgsConstructor
public class Item extends BaseData<ItemId> {

    private String name;

    private BigDecimal price;

    private UUID userId;

    public Item(ItemId itemId) {
        super(itemId);
    }

    public Item(Item item) {
        super(item.getId());
        this.name = item.getName();
        this.price = item.getPrice();
        this.userId = item.getUserId();
    }

    public Item updateItem(Item item) {
        this.name = item.getName();
        this.price = item.getPrice();
        this.userId = item.getUserId();
        return this;
    }


}
