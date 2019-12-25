package com.letsup.habit.app.backend.app;

import com.letsup.habit.app.backend.base.BaseController;
import com.letsup.habit.app.backend.constants.ApiResultConstants;
import com.letsup.habit.app.backend.service.HabHabitTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(description = "习惯模板")
@RequestMapping("/app/template")
public class HabitTemplateApp extends BaseController {
    @Autowired
    private HabHabitTemplateService habHabitTemplateService;

    /**
     * 根据模板标签获得模板列表
     * @return
     */
    @ApiOperation(value = "根据模板标签获得模板列表")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/getTemplateByTag", method = RequestMethod.GET)
    public String getTemplateByTag(HttpServletRequest request) {
        return getResponse(ApiResultConstants.SUCCESS, "", habHabitTemplateService.getByTag(request.getParameter("tag")));
    }

    /**
     * 根据推荐获得模板
     * @return
     */
    @ApiOperation(value = "根据推荐获得模板")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/getRecommend/{memberId}", method = RequestMethod.GET)
    public String getRecommend(@PathVariable("memberId") Long memberId, HttpServletRequest request) {
        return getResponse(ApiResultConstants.SUCCESS, "", habHabitTemplateService.getByMemberId(memberId));
    }

    /**
     * 根据id获得模板详情
     * @return
     */
    @ApiOperation(value = "根据id获得模板详情")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getById(@PathVariable("id") Long id, HttpServletRequest request) {
        return getResponse(ApiResultConstants.SUCCESS, "", habHabitTemplateService.getById(id));
    }
}