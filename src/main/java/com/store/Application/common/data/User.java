package com.store.Application.common.data;

import com.store.Application.dao.model.Roles;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
//uses non-static and non-transient fields to generate equals and hashCode methods  more info found here : https://projectlombok.org/features/EqualsAndHashCode
@Data // for getter and setter
//@ToString // for to string method
//@NoArgsConstructor
// annotation is used to generate the no-argument constructor for a class. In this case, the class consists of final fields.
public class User extends BaseData<UserId> {

    private static final long serialVersionUID = 44L;

    private String userName;

    private String password;

    private String email;

    private Roles roles;


    private UUID userId;


    public User() {
        super();
    }

    public User(UserId id) {
        super(id);
    }

    public User(User user) {
        super(user);
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.roles = user.getRoles();
        this.userId = user.getUserId();
    }

    public User updateUser(User user) {
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles();
        this.userId = user.getUserId();
        return this;
    }


    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", userId=" + userId +
                ", createdTime=" + createdTime +
                ", id=" + id +
                '}';
    }
}
