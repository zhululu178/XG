package com.letsup.habit.app.backend.security.service;

import com.letsup.habit.app.backend.cond.RegisterUserCond;
import com.letsup.habit.app.backend.dao.entity.HabAppRole;
import com.letsup.habit.app.backend.dao.entity.HabAppUser;
import com.letsup.habit.app.backend.security.SecurityUser;
import com.letsup.habit.app.backend.service.HabRoleService;
import com.letsup.habit.app.backend.service.HabUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private HabUserService habAppUserService;

    @Autowired
    private HabRoleService habAppRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HabAppUser user = habAppUserService.getByIMEI(username);
        if(user == null) {
            RegisterUserCond registerUserCond = new RegisterUserCond();
            registerUserCond.setImei(username);
            user = habAppUserService.add(registerUserCond);
//            throw new UsernameNotFoundException("用戶名不存在");
        }
        return generateJwtUser(user);
    }

    private UserDetails generateJwtUser(HabAppUser user) {
        //这里要从db获取
        List<HabAppRole> roles = habAppRoleService.getByUserId(user.getId());
        Collection<GrantedAuthority> authorities = null;
        if(roles != null && roles.size() > 0) {
            authorities = new ArrayList<>(roles.size());
            for(HabAppRole role : roles) {
                authorities.add(new SimpleGrantedAuthority(role.getCode()));
            }
        }
        return new SecurityUser(user, authorities);
    }
}
