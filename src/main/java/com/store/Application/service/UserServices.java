package com.store.Application.service;

import com.store.Application.common.data.User;
import com.store.Application.common.data.UserId;

import java.util.List;
import java.util.UUID;

public interface UserServices {

    List<User> getAllUser(UUID userId);

    User saveUser(UserId userId, User user) throws Exception;

    User findUserById(UUID uuid);

}
