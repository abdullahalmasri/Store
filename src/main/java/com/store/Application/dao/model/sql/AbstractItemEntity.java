package com.store.Application.dao.model.sql;

import com.store.Application.common.data.Item;
import com.store.Application.common.data.ItemId;
import com.store.Application.common.data.UserId;
import com.store.Application.dao.model.BaseEntity;
import com.store.Application.dao.model.BaseSqlEntity;
import com.store.Application.dao.model.ModelEntity;
import com.store.Application.dao.util.mapping.JsonBinaryType;
import com.store.Application.dao.util.mapping.JsonStringType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.xml.ws.Service;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@MappedSuperclass
public abstract class AbstractItemEntity<T extends Item> extends BaseSqlEntity<T> implements BaseEntity<T>, Serializable {

    @Column(name = ModelEntity.PRODUCT_NAME)
    private String name;
    @Column(name = ModelEntity.PRODUCT_PRICE, nullable = false)
    private BigDecimal price;
    @Column(name = ModelEntity.USER_ID, columnDefinition = "uuid")
    private UUID userId;

    public AbstractItemEntity() {
        super();
    }

    public AbstractItemEntity(Item item) {
        if (item.getId() != null) {
            this.setId(item.getUuidId());
        }
        this.setCreatedTime(item.getCreatedTime());
        this.name = item.getName();
        this.price = item.getPrice();
        this.userId = item.getUserId();
    }

    public AbstractItemEntity(ItemEntity itemEntity) {
        this.setId(itemEntity.getUuid());
        this.setCreatedTime(itemEntity.getCreatedTime());
        this.setName(itemEntity.getName());
        this.setPrice(itemEntity.getPrice());
        this.setUserId(itemEntity.getUserId());

    }


    protected Item toItem() {
        Item item = new Item(new ItemId(getUuid()));
        item.setCreatedTime(getCreatedTime());
        item.setName(name);
        item.setPrice(price);
        item.setUserId(userId);
        return item;
    }
}
