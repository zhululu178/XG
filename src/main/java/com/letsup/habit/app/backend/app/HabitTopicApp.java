package com.letsup.habit.app.backend.app;

import com.letsup.habit.app.backend.base.BaseController;
import com.letsup.habit.app.backend.constants.ApiResultConstants;
import com.letsup.habit.app.backend.service.HabHabitTopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(description = "习惯专题")
@RequestMapping("/app/topic")
public class HabitTopicApp extends BaseController {
    @Autowired
    private HabHabitTopicService habHabitTopicService;

    /**
     * 获得习惯专题列表
     * @return
     */
    @ApiOperation(value = "获得习惯专题列表")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/getTopics", method = RequestMethod.GET)
    public String getFamilyRoles(HttpServletRequest request) {
        return getResponse(ApiResultConstants.SUCCESS, "", habHabitTopicService.getAll());
    }

    /**
     * 根据id获得专题详情
     * @return
     */
    @ApiOperation(value = "根据id获得专题详情")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getById(@PathVariable("id") Long id, HttpServletRequest request) {
        return getResponse(ApiResultConstants.SUCCESS, "", habHabitTopicService.getById(id));
    }
}
