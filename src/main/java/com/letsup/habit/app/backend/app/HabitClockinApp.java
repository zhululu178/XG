package com.letsup.habit.app.backend.app;

import com.letsup.habit.app.backend.base.BaseController;
import com.letsup.habit.app.backend.base.GlobalException;
import com.letsup.habit.app.backend.cond.ClockinCond;
import com.letsup.habit.app.backend.cond.CreateHabitLogCond;
import com.letsup.habit.app.backend.constants.ApiResultConstants;
import com.letsup.habit.app.backend.dao.entity.HabAppUser;
import com.letsup.habit.app.backend.service.HabHabitClockinService;
import com.letsup.habit.app.backend.util.PrincipalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(description = "习惯打卡")
@RequestMapping("/app/clockin")
public class HabitClockinApp extends BaseController {
    @Autowired
    private HabHabitClockinService habHabitClockinService;

    /**
     * 打卡指定习惯
     * @return
     */
    @ApiOperation(value = "根据习惯id进行打卡")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String getById(@RequestBody ClockinCond clockinCond, HttpServletRequest request) {
        HabAppUser user = PrincipalUtil.getAppUser(request);
        try {
            return getResponse(ApiResultConstants.SUCCESS, "", habHabitClockinService.clockIn(user.getId(), clockinCond));
        } catch (GlobalException e) {
            e.printStackTrace();
            this.logger.error("打卡失败，" + e.getMessage());
            return getResponse(e);
        }
    }

    /**
     * 今日不做
     * @return
     */
    @ApiOperation(value = "今日不做")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/notDoToday", method = RequestMethod.POST)
    public String notDoToday(@RequestBody CreateHabitLogCond createHabitLogCond, HttpServletRequest request) {
        HabAppUser user = PrincipalUtil.getAppUser(request);
        try {
            habHabitClockinService.notDoToday(user.getId(), createHabitLogCond);
            return getResponse(ApiResultConstants.SUCCESS, "", null);
        } catch (GlobalException e) {
            e.printStackTrace();
            this.logger.error("今日不做失败，" + e.getMessage());
            return getResponse(e);
        }
    }

    /**
     * 撤销打卡
     * @return
     */
    @ApiOperation(value = "撤销打卡")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/cancel/{habitId}", method = RequestMethod.GET)
    public String cancel(@PathVariable("habitId") Long habitId, HttpServletRequest request) {
        HabAppUser user = PrincipalUtil.getAppUser(request);
        try {
            return getResponse(ApiResultConstants.SUCCESS, "", habHabitClockinService.cancelClockIn(user.getId(), habitId));
        } catch (GlobalException e) {
            e.printStackTrace();
            this.logger.error("撤销打卡失败，" + e.getMessage());
            return getResponse(e);
        }
    }

    /**
     * 打卡
     * @return
     */
    @ApiOperation(value = "打卡详情")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(@RequestParam(value = "habitId") Long habitId, @RequestParam(value = "date") String date) {
        try {
            return getResponse(ApiResultConstants.SUCCESS, "", habHabitClockinService.getByHabitId(habitId, date));
        } catch (GlobalException e) {
            e.printStackTrace();
            this.logger.error("打卡详情获取失败，" + e.getMessage());
            return getResponse(e);
        }
    }
}