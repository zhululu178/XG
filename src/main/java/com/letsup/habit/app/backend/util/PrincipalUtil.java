package com.letsup.habit.app.backend.util;

import com.letsup.habit.app.backend.dao.entity.HabAppUser;
import com.letsup.habit.app.backend.security.SecurityUser;

import javax.servlet.http.HttpServletRequest;

public class PrincipalUtil {
    /**
     * 獲得用戶信息
     * @param request
     * @return
     */
    public static SecurityUser getSecurityUser(HttpServletRequest request) {
        SecurityUser su = (SecurityUser) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return su;
    }

    /**
     * 獲得app用戶信息
     * @param request
     * @return
     */
    public static HabAppUser getAppUser(HttpServletRequest request) {
        SecurityUser su = getSecurityUser(request);
        if(su != null) {
            return su.getAppUser();
        }
        return null;
    }

    /**
     * 獲得app用戶id
     * @param request
     * @return
     */
    public static Long getUserId(HttpServletRequest request) {
        SecurityUser su = getSecurityUser(request);
        if(su != null) {
            return su.getId();
        }
        return null;
    }
}
