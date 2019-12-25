package com.letsup.habit.app.backend.app;

import com.letsup.habit.app.backend.base.BaseController;
import com.letsup.habit.app.backend.base.GlobalException;
import com.letsup.habit.app.backend.cond.AppLoginCond;
import com.letsup.habit.app.backend.cond.RegisterUserCond;
import com.letsup.habit.app.backend.constants.ApiResultConstants;
import com.letsup.habit.app.backend.dao.entity.HabAppUser;
import com.letsup.habit.app.backend.service.HabUserService;
import com.letsup.habit.app.backend.util.JwtTokenUtil;
import com.letsup.habit.app.backend.vo.TokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.letsup.habit.app.backend.constants.ApiResultConstants.ExceptionMessage.LOGIN_INPUT_ERROR;

@RestController
public class LoginApp extends BaseController {

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private HabUserService appUserService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

//    @ApiOperation(value = "登录")
//    @RequestMapping(value = "/login1", method = RequestMethod.POST)
    public String login(@RequestBody AppLoginCond appUser, HttpServletRequest request) {
        HabAppUser loginUser = null;
        try {
            loginUser = this.appUserService.loginCheck(appUser);
            if(loginUser == null) {
                throw new GlobalException(LOGIN_INPUT_ERROR);
            }
        } catch (GlobalException e) {
            e.printStackTrace();
            return this.getResponse(e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(appUser.getUsername());
        TokenVo token = new TokenVo();
        token.setAccess_token(jwtTokenUtil.generateAccessToken(userDetails));
        token.setRefresh_token(jwtTokenUtil.generateRefreshToken(userDetails));
        return this.getResponse(ApiResultConstants.SUCCESS, "", token);
    }

//    @ApiOperation(value = "通过IMEI登录")
//    @RequestMapping(value = "/loginWithIMEI", method = RequestMethod.POST)
    public String loginWithIMEI(@RequestBody AppLoginCond appUser, HttpServletRequest request) {
        HabAppUser loginUser = this.appUserService.getByIMEI(appUser.getImei());
        if(loginUser == null) {
            RegisterUserCond registerUserCond = new RegisterUserCond();
            registerUserCond.setImei(appUser.getImei());
            this.appUserService.add(registerUserCond);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(appUser.getImei());
        TokenVo token = new TokenVo();
        token.setAccess_token(jwtTokenUtil.generateAccessToken(userDetails));
        token.setRefresh_token(jwtTokenUtil.generateRefreshToken(userDetails));
        return this.getResponse(ApiResultConstants.SUCCESS, "", token);
    }
}