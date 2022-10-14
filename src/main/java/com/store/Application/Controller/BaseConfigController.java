package com.store.Application.Controller;

import com.store.Application.common.data.UserId;
import com.store.Application.configuration.UserAut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BaseConfigController {

    protected UserId getCurrentUser() {

        UserAut authentication = (UserAut) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (authentication != null) {
            return  authentication.getUserId();
        } else {
            return null;
        }
    }

}
