package com.letsup.habit.app.backend.app;

import com.letsup.habit.app.backend.base.BaseController;
import com.letsup.habit.app.backend.constants.ApiResultConstants;
import com.letsup.habit.app.backend.service.HabHabitBgmService;
import com.letsup.habit.app.backend.service.HabHabitColorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(description = "习惯背景音乐")
@RequestMapping("/app/bgm")
public class HabitBgmApp extends BaseController {
    @Autowired
    private HabHabitBgmService habHabitBgmService;

    /**
     * 获得习惯背景音乐列表
     * @return
     */
    @ApiOperation(value = "获得习惯背景音乐列表")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/getBgms", method = RequestMethod.GET)
    public String getBgms() {
        return getResponse(ApiResultConstants.SUCCESS, "", habHabitBgmService.getAll());
    }
}