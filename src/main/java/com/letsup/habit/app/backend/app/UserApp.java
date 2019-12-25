package com.letsup.habit.app.backend.app;

import com.letsup.habit.app.backend.base.BaseController;
import com.letsup.habit.app.backend.cond.RegisterUserCond;
import com.letsup.habit.app.backend.constants.ApiResultConstants;
import com.letsup.habit.app.backend.dao.entity.HabAppUser;
import com.letsup.habit.app.backend.security.SecurityUser;
import com.letsup.habit.app.backend.service.HabUserService;
import com.letsup.habit.app.backend.util.PrincipalUtil;
import com.letsup.habit.app.backend.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(description = "用户设置-通用功能")
@RequestMapping("/app/user")
public class UserApp extends BaseController {
    @Autowired
    private HabUserService habAppUserService;

    /**
     * 个人信息
     * @return
     */
    @ApiOperation(value = "个人信息")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public String getUser(HttpServletRequest request) {
        return getResponse(ApiResultConstants.SUCCESS, "", habAppUserService.getById(PrincipalUtil.getUserId(request)));
    }

    /**
     * 用户注册
     * @return
     */
    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody RegisterUserCond registerUserCond, HttpServletRequest request) {
        return getResponse(ApiResultConstants.SUCCESS, "", this.habAppUserService.add(registerUserCond));
    }
}
