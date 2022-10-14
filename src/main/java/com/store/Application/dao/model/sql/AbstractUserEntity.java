package com.store.Application.dao.model.sql;

import com.store.Application.common.data.User;
import com.store.Application.common.data.UserId;
import com.store.Application.dao.model.BaseEntity;
import com.store.Application.dao.model.BaseSqlEntity;
import com.store.Application.dao.model.ModelEntity;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import com.store.Application.dao.model.Roles;
import com.store.Application.dao.util.mapping.JsonBinaryType;
import com.store.Application.dao.util.mapping.JsonStringType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@MappedSuperclass
public abstract class AbstractUserEntity<T extends User> extends BaseSqlEntity<T> implements BaseEntity<T>, Serializable {

    @Column(name = ModelEntity.USERNAME_PROPERTY)
    private String userName;

    @Transient
    // here you need to be sure that import come from org.springframework.data.annotation so it specifically states to the spring framework that the Object Mapper you are using should not include this value when converting from Java Object to JSON.
    @Column(name = ModelEntity.PASSWORD_PROPERTY)
    private String password;

    @Column(name = ModelEntity.EMAIL_PROPERTY, unique = true)
    private String email;

    @Column(name = ModelEntity.AUTHORIZE)
    @Enumerated(EnumType.STRING)
    private Roles roles;

    @Column(name = ModelEntity.USER_ID, columnDefinition = "uuid")
    private UUID userId;


    public AbstractUserEntity() {
        super();
    }

    public AbstractUserEntity(User user) {
        if (user.getId() != null) {
            this.setId(user.getUuidId());
        }
        this.setCreatedTime(user.getCreatedTime());
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles();
        this.userId = user.getUserId();
    }

    public AbstractUserEntity(UserEntity userEntity) {
        this.setId(userEntity.getId());
        this.setCreatedTime(userEntity.getCreatedTime());
        this.setEmail(userEntity.getUserName());
        this.setPassword(userEntity.getPassword());
        this.setEmail(userEntity.getEmail());
        this.setRoles(userEntity.getRoles());
        this.setUserId(userEntity.getUserId());
    }


    protected User toUser() {
        User user = new User(new UserId(getId()));
        user.setCreatedTime(getCreatedTime());
        user.setEmail(email);
        user.setPassword(password);
        user.setUserName(userName);
        user.setRoles(roles);
        user.setUserId(userId);
        return user;
    }
}
