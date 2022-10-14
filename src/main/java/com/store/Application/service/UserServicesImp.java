package com.store.Application.service;

import com.store.Application.common.data.User;
import com.store.Application.common.data.UserId;
import com.store.Application.dao.model.Repository.UserRepository;
import com.store.Application.dao.model.sql.UserEntity;
import com.store.Application.validation.ValidationEntity;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("userServices")
@Slf4j
public class UserServicesImp extends ValidationEntity implements UserServices {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public UserServicesImp(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<User> getAllUser(UUID userId) {
        return (userRepository.findAll(userId)).stream().map(userEntity -> userEntity.toData()).collect(Collectors.toList());
    }

    @Override
    public User saveUser(UserId userId, User user) throws Exception {

        if (user.getId() == null) {
            user = validationTypeImp(user, User.class);
            log.info("the thing {}", user);
        }
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        userRepository.save(userEntity);

        return user;
    }

    @Override
    public User findUserById(UUID uuid) {
        User user = (userRepository.findById(uuid)).orElseGet(()->null).toData();
        return user;
    }
}
