package com.letsup.habit.app.backend.app;

import com.letsup.habit.app.backend.base.BaseController;
import com.letsup.habit.app.backend.base.GlobalException;
import com.letsup.habit.app.backend.constants.ApiResultConstants;
import com.letsup.habit.app.backend.dao.entity.HabAppUser;
import com.letsup.habit.app.backend.service.HabHabitBonusService;
import com.letsup.habit.app.backend.util.PrincipalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(description = "习惯奖励(星星)")
@RequestMapping("/app/bonus")
public class HabitBonusApp extends BaseController {
    @Autowired
    private HabHabitBonusService habHabitBonusService;

    /**
     * 收集星星
     * @return
     */
    @ApiOperation(value = "收集星星")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/collect/{dailyRecordId}", method = RequestMethod.GET)
    public String cancel(@PathVariable("dailyRecordId") Long dailyRecordId, HttpServletRequest request) {
        HabAppUser user = PrincipalUtil.getAppUser(request);
        try {
            return getResponse(ApiResultConstants.SUCCESS, "", habHabitBonusService.collectBonus(user.getId(), dailyRecordId));
        } catch (GlobalException e) {
            e.printStackTrace();
            this.logger.error("收集星星失败，" + e.getMessage());
            return getResponse(e);
        }
    }

    /**
     * 可收集星星列表
     * @return
     */
    @ApiOperation(value = "可收集星星列表")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/getPageByCond", method = RequestMethod.GET)
    public String getPageByCond(@RequestParam(value = "memberId") Long memberId,
                                  @RequestParam(value = "pageIndex") Integer pageIndex, @RequestParam(value = "pageCount") Integer pageCount) {
        return getResponse(ApiResultConstants.SUCCESS, "", this.habHabitBonusService.getPageByCond(memberId, pageIndex, pageCount));
    }
}
