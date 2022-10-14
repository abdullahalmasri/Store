package com.store.Application.service;

import com.store.Application.common.data.User;
import com.store.Application.configuration.UserAut;
import com.store.Application.dao.model.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class CustomUserDetailServiceImp implements CustomUserDetailService {

    private final UserRepository userRepository;

    public CustomUserDetailServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserAut loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = (userRepository.findUserEntityByUserName(username)).toData();
        UserAut userAut = new UserAut(user.getUserName(), user.getPassword(), new ArrayList<>(),user.getId());
        return userAut;
    }
}
