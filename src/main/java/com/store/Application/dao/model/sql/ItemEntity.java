package com.store.Application.dao.model.sql;

import com.store.Application.common.data.Item;
import com.store.Application.dao.model.ModelEntity;
import com.store.Application.dao.util.mapping.JsonBinaryType;
import com.store.Application.dao.util.mapping.JsonStringType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Table(name = ModelEntity.ITEM_TABLE)
public final class ItemEntity extends AbstractItemEntity<Item> {
    public ItemEntity() {
        super();
    }

    public ItemEntity(Item item) {
        super(item);
    }

    @Override
    public Item toData() {
        return super.toItem();
    }


}
