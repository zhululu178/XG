package com.letsup.habit.app.backend.security.crypto;

import com.letsup.habit.app.backend.util.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 不需要密碼
 */
public class NoPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return "";
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return true;
    }
}
