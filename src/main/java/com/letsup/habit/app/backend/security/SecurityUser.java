package com.letsup.habit.app.backend.security;

import com.letsup.habit.app.backend.dao.entity.HabAppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class SecurityUser implements UserDetails {
    private static final long serialVersionUID = 1L;
    private Collection<GrantedAuthority> authorities;
    private HabAppUser appUser;

    public SecurityUser(HabAppUser appUser, Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
        this.appUser = appUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return appUser.getPassword();
    }

    @Override
    public String getUsername() {
        return appUser.getImei();
    }

    //账户是否未过期,过期无法验证
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //指定用户是否解锁,锁定的用户无法进行身份验证
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //指示是否已过期的用户的凭据(密码),过期的凭据防止认证
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //是否可用 ,禁用的用户不能身份验证
    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return this.appUser.getId();
    }

    public HabAppUser getAppUser() {
        return appUser;
    }
}
