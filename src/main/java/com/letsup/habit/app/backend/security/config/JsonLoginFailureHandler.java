package com.letsup.habit.app.backend.security.config;

import com.letsup.habit.app.backend.base.BaseController;
import com.letsup.habit.app.backend.constants.ApiResultConstants;
import com.letsup.habit.app.backend.util.JwtTokenUtil;
import com.letsup.habit.app.backend.vo.TokenVo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Json登录处理器
 *
 * @author:
 * @create:
 * To change this template use File | Settings | File Templates.
 */
public class JsonLoginFailureHandler extends BaseController implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        try {
            OutputStream out = response.getOutputStream();
            response.setContentType("application/json;charset=utf-8");
            out.write(getResponse(ApiResultConstants.ExceptionMessage.LOGIN_INPUT_ERROR.getCode(), ApiResultConstants.ExceptionMessage.LOGIN_INPUT_ERROR.getMessage(), null).getBytes());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
