package com.letsup.habit.app.backend.app;

import com.letsup.habit.app.backend.base.BaseController;
import com.letsup.habit.app.backend.constants.ApiResultConstants;
import com.letsup.habit.app.backend.service.HabHabitIconService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(description = "习惯图标")
@RequestMapping("/app/icon")
public class HabitIconApp extends BaseController {
    @Autowired
    private HabHabitIconService habHabitIconService;

    /**
     * 获得习惯图标列表
     * @return
     */
    @ApiOperation(value = "获得习惯图标列表")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/getIcons", method = RequestMethod.GET)
    public String getIcons() {
        return getResponse(ApiResultConstants.SUCCESS, "", habHabitIconService.getAll());
    }
}