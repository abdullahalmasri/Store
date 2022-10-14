package com.store.Application.configuration;

import com.store.Application.common.data.UserId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserAut extends User {

    public static final long serialVersionUID = 255L;

    private Collection<GrantedAuthority> grantedAuthorities;
    private PrincipalAuth principalAuth;
    private boolean valid;
    private UserId userId;

    public Collection<GrantedAuthority> getGrantedAuthorities() {
        return grantedAuthorities;
    }

    public void setGrantedAuthorities(Collection<GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public UserAut(String username,
                   String password,
                   Collection<? extends GrantedAuthority> authorities,
                   UserId userId) {
        super(username, password, authorities);
        this.userId = userId;
    }

    public UserAut(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public UserAut(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }




    public boolean isValid() {
        return valid;
    }

    public PrincipalAuth getPrincipalAuth() {
        return principalAuth;
    }
//
    public void setPrincipalAuth(PrincipalAuth principalAuth) {
        this.principalAuth = principalAuth;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
