package com.store.Application.Controller;

import com.store.Application.common.data.User;
import com.store.Application.common.data.UserId;
import com.store.Application.dao.util.JwtUtil;
import com.store.Application.service.UserServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController extends BaseConfigController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServices userServices;

    @GetMapping("/")
    public String welcome() {
        return "templates/index.html";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public void saveUser(@RequestBody User user) {
        UserId currentUserId = getCurrentUser();
//        log.info("the current user {}", getCurrentUser().getId());
        user.setUserId(currentUserId.getId());
        log.info("The entered user in process of saving {}", user);

        try {
            userServices.saveUser(currentUserId, user);
        } catch (Exception e) {
            log.info("There is Exception {} please check again info", e.getMessage());
        }
    }

    @RequestMapping(value = "/getToken", method = RequestMethod.POST)
    @ResponseBody
    public String getToken(
            @RequestBody User user) throws Exception {

        log.info("the token will be generated for user {}", user.getUserName());
        try {
            authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    user.getUserName(), user.getPassword())
                    );
        } catch (Exception e) {
            throw new Exception("invalid username or password");
        }
        return jwtUtil.generateToken(user.getUserName());
    }


    @RequestMapping(value = "/auth/logout", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
//        new SecurityContextLogoutHandler().logout(request, null, null);
//        try {
//            log.info("The login process to return login page");
//            //sending back to client app
//            response.sendRedirect(request.getHeader("/"));
//            log.info("you logout successfully");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        boolean isSecure = false;
        String contextPath = null;
        if (request != null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            isSecure = request.isSecure();
            contextPath = request.getContextPath();
        }
        SecurityContext context = SecurityContextHolder.getContext();
        SecurityContextHolder.clearContext();
        context.setAuthentication(null);
        if (response != null) {
            Cookie cookie = new Cookie("JSESSIONID", null);
            String cookiePath = StringUtils.hasText(contextPath) ? contextPath : "/";
            cookie.setPath(cookiePath);
            cookie.setMaxAge(0);
            cookie.setSecure(isSecure);
            response.addCookie(cookie);
        }
    }


}
