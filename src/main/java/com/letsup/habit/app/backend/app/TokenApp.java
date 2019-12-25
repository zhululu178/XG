package com.letsup.habit.app.backend.app;

import com.letsup.habit.app.backend.base.BaseController;
import com.letsup.habit.app.backend.base.GlobalException;
import com.letsup.habit.app.backend.constants.ApiResultConstants;
import com.letsup.habit.app.backend.security.SecurityUser;
import com.letsup.habit.app.backend.util.JwtTokenUtil;
import com.letsup.habit.app.backend.util.PrincipalUtil;
import com.letsup.habit.app.backend.vo.TokenVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(description = "token管理")
@RequestMapping("/app/token")
public class TokenApp extends BaseController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 刷新token
     * @return
     */
    @ApiOperation(value = "刷新token")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public String refresh(HttpServletRequest request) {
        TokenVo token = new TokenVo();
        token.setAccess_token(jwtTokenUtil.generateAccessToken(PrincipalUtil.getSecurityUser(request).getUsername()));
        token.setRefresh_token(jwtTokenUtil.generateRefreshToken(PrincipalUtil.getSecurityUser(request).getUsername()));
        return this.getResponse(ApiResultConstants.SUCCESS, "", token);
    }
}