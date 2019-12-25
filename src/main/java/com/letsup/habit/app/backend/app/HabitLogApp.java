package com.letsup.habit.app.backend.app;

import com.letsup.habit.app.backend.base.BaseController;
import com.letsup.habit.app.backend.cond.CreateHabitLogCond;
import com.letsup.habit.app.backend.constants.ApiResultConstants;
import com.letsup.habit.app.backend.dao.entity.HabAppUser;
import com.letsup.habit.app.backend.service.HabHabitLogService;
import com.letsup.habit.app.backend.util.PrincipalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(description = "习惯日志")
@RequestMapping("/app/log")
public class HabitLogApp extends BaseController {
    @Autowired
    private HabHabitLogService habHabitLogService;

    /**
     * 创建习惯日志记录
     * @return
     */
    @ApiOperation(value = "创建习惯日志")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestBody CreateHabitLogCond createHabitLogCond, HttpServletRequest request) {
        HabAppUser user = PrincipalUtil.getAppUser(request);
        habHabitLogService.add(user.getId(), createHabitLogCond);
        return getResponse(ApiResultConstants.SUCCESS, "", null);
    }

    /**
     * 修改习惯日志记录
     * @return
     */
    @ApiOperation(value = "修改习惯日志记录")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String modify(@RequestBody CreateHabitLogCond createHabitLogCond, HttpServletRequest request) {
        HabAppUser user = PrincipalUtil.getAppUser(request);
        habHabitLogService.modify(user.getId(), createHabitLogCond);
        return getResponse(ApiResultConstants.SUCCESS, "", null);
    }

    /**
     * 删除习惯日志记录
     * @return
     */
    @ApiOperation(value = "删除习惯日志记录")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable("id") Long id, HttpServletRequest request) {
        HabAppUser user = PrincipalUtil.getAppUser(request);
        habHabitLogService.delete(user.getId(), id);
        return getResponse(ApiResultConstants.SUCCESS, "", null);
    }

    /**
     * 根据id获得习惯日志详情
     * @return
     */
    @ApiOperation(value = "根据id获得习惯日志详情")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getById(@PathVariable("id") Long id) {
        return getResponse(ApiResultConstants.SUCCESS, "", habHabitLogService.getById(id));
    }

    /**
     * 根据习惯id获得最新一条日志详情
     * @return
     */
    @ApiOperation(value = "根据习惯id和日期获得最新一条日志详情")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/last", method = RequestMethod.GET)
    public String getLastLogByHabitId(@RequestParam(value = "habitId") Long habitId, @RequestParam(value = "date") String logDate) {
        return getResponse(ApiResultConstants.SUCCESS, "", habHabitLogService.getLastLogByHabitId(habitId, logDate));
    }
}