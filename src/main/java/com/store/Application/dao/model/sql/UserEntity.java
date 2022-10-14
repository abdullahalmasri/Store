package com.store.Application.dao.model.sql;

import com.store.Application.common.data.User;
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
@Table(name = ModelEntity.USER_TABLE)
public final class UserEntity extends AbstractUserEntity<User> {
    public UserEntity() {
        super();
    }

    public UserEntity(User user){
        super(user);
    }

    @Override
    public User toData() {
        return super.toUser();
    }
}
