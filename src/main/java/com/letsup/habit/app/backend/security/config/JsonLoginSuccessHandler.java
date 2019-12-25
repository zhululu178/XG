package com.letsup.habit.app.backend.security.config;

import com.letsup.habit.app.backend.base.BaseController;
import com.letsup.habit.app.backend.constants.ApiResultConstants;
import com.letsup.habit.app.backend.security.SecurityUser;
import com.letsup.habit.app.backend.util.JwtTokenUtil;
import com.letsup.habit.app.backend.vo.TokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * Json登录处理器
 *
 * @author:
 * @create:
 * To change this template use File | Settings | File Templates.
 */
public class JsonLoginSuccessHandler extends BaseController implements AuthenticationSuccessHandler {
    private JwtTokenUtil jwtTokenUtil;

    public JsonLoginSuccessHandler(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication){
        TokenVo token = new TokenVo();
        SecurityUser su = (SecurityUser)authentication.getPrincipal();
        token.setAccess_token(jwtTokenUtil.generateAccessToken(su.getUsername()));
        token.setRefresh_token(jwtTokenUtil.generateRefreshToken(su.getUsername()));
        response.setContentType("application/json;charset=utf-8");
        try {
            OutputStream out = response.getOutputStream();
            out.write(getResponse(ApiResultConstants.SUCCESS, "",token ).getBytes());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
