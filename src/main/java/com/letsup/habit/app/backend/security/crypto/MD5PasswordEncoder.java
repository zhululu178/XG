package com.letsup.habit.app.backend.security.crypto;

import com.letsup.habit.app.backend.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * MD5密碼加密器
 */
public class MD5PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return MD5Util.encode((String)rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if(StringUtils.isNotEmpty(encodedPassword)) {
            return encodedPassword.equals(MD5Util.encode((String)rawPassword));
        } else {
            return false;
        }
    }
}