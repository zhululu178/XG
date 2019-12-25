package com.letsup.habit.app.backend.app;

import com.letsup.habit.app.backend.base.BaseController;
import com.letsup.habit.app.backend.base.GlobalException;
import com.letsup.habit.app.backend.cond.CreateHabitCond;
import com.letsup.habit.app.backend.cond.CreateHabitFromTemplateCond;
import com.letsup.habit.app.backend.cond.EndDateCond;
import com.letsup.habit.app.backend.constants.ApiResultConstants;
import com.letsup.habit.app.backend.dao.entity.HabAppUser;
import com.letsup.habit.app.backend.service.HabHabitService;
import com.letsup.habit.app.backend.util.DateTimeUtil;
import com.letsup.habit.app.backend.util.HabitEndDateUtil;
import com.letsup.habit.app.backend.util.PrincipalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@Api(description = "习惯功能")
@RequestMapping("/app/habit")
public class HabitApp extends BaseController {
    @Autowired
    private HabHabitService habHabitService;

    /**
     * 根据习惯模板创建习惯
     * @return
     */
    @ApiOperation(value = "根据习惯模板创建习惯")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/createByTemplate", method = RequestMethod.POST)
    public String createByTemplate(@RequestBody CreateHabitFromTemplateCond createHabitFromTemplateCond, HttpServletRequest request) {
        HabAppUser user = PrincipalUtil.getAppUser(request);
        try {
            habHabitService.addByTemplate(user.getFamilyId(), user.getId(), createHabitFromTemplateCond);
            return getResponse(ApiResultConstants.SUCCESS, "", null);
        }  catch (GlobalException e) {
            this.logger.debug("从模板创建习惯失败. " + e.getMessage());
            e.printStackTrace();
            return this.getResponse(e);
        }
    }

    /**
     * 习惯删除
     * @return
     */
    @ApiOperation(value = "习惯删除")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, HttpServletRequest request) {
        HabAppUser user = PrincipalUtil.getAppUser(request);
        try {
            habHabitService.delete(id, user.getId());
            return getResponse(ApiResultConstants.SUCCESS, "", null);
        }  catch (GlobalException e) {
            this.logger.debug("习惯删除失败. " + e.getMessage());
            e.printStackTrace();
            return this.getResponse(e);
        }
    }

    /**
     * 习惯放弃
     * @return
     */
    @ApiOperation(value = "习惯放弃")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/giveup/{id}", method = RequestMethod.GET)
    public String giveUp(@PathVariable("id") Long id, HttpServletRequest request) {
        HabAppUser user = PrincipalUtil.getAppUser(request);
        try {
            habHabitService.giveUp(id, user.getId());
            return getResponse(ApiResultConstants.SUCCESS, "", null);
        }  catch (GlobalException e) {
            this.logger.debug("习惯放弃失败. " + e.getMessage());
            e.printStackTrace();
            return this.getResponse(e);
        }
    }

    /**
     * 根据习惯模板创建习惯
     * @return
     */
    @ApiOperation(value = "根据习惯模板创建习惯")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestBody CreateHabitCond createHabitCond, HttpServletRequest request) {
        HabAppUser user = PrincipalUtil.getAppUser(request);
        try {
            habHabitService.add(user.getFamilyId(), user.getId(), createHabitCond);
            return getResponse(ApiResultConstants.SUCCESS, "", null);
        }  catch (GlobalException e) {
            this.logger.debug("创建习惯失败. " + e.getMessage());
            e.printStackTrace();
            return this.getResponse(e);
        }
    }

    /**
     * 根据习惯模板修改习惯
     * @return
     */
    @ApiOperation(value = "根据习惯模板修改习惯")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String modify(@RequestBody CreateHabitCond createHabitCond, HttpServletRequest request) {
        HabAppUser user = PrincipalUtil.getAppUser(request);
        try {
            habHabitService.modify(user.getFamilyId(), user.getId(), createHabitCond);
            return getResponse(ApiResultConstants.SUCCESS, "", null);
        }  catch (GlobalException e) {
            this.logger.debug("修改习惯失败. " + e.getMessage());
            e.printStackTrace();
            return this.getResponse(e);
        }
    }

    /**
     * 根据家庭和日期获得习惯列表
     * @return
     */
    @ApiOperation(value = "根据家庭和日期获得习惯列表")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/getByFamilyIdAndDate", method = RequestMethod.GET)
    public String getByFamilyIdAndDate(@RequestParam(value = "date") String date, HttpServletRequest request) {
        HabAppUser user = PrincipalUtil.getAppUser(request);
        String today = DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd");
        if(today.compareTo(date) > 0) {
            return getResponse(ApiResultConstants.SUCCESS, "", this.habHabitService.getBeforeTodayHabitByFamilyId(user.getFamilyId(), date));
        } else {
            return getResponse(ApiResultConstants.SUCCESS, "", this.habHabitService.getFromTodayOnHabitByFamilyId(user.getFamilyId(), date));
        }
    }

    /**
     * 根据状态分页查询习惯
     * @return
     */
    @ApiOperation(value = "根据条件分页查询习惯")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/getPageByCond", method = RequestMethod.GET)
    public String getPageByStatus(@RequestParam(value = "status") String status,
                                  @RequestParam(value = "pageIndex") Integer pageIndex, @RequestParam(value = "pageCount") Integer pageCount, HttpServletRequest request) {
        HabAppUser user = PrincipalUtil.getAppUser(request);
        return getResponse(ApiResultConstants.SUCCESS, "", this.habHabitService.getPageByCond(user.getFamilyId(), status, pageIndex, pageCount));
    }

    /**
     * 根据习惯id，获得习惯详情
     * @return
     */
    @ApiOperation(value = "根据习惯id，获得习惯详情")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getById(@PathVariable("id") Long id) {
        return getResponse(ApiResultConstants.SUCCESS, "", habHabitService.getById(id));
    }

    /**
     * 根据习惯id，再来一次习惯
     * @return
     */
    @ApiOperation(value = "根据习惯id，再来一次习惯")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/copy/{id}", method = RequestMethod.GET)
    public String copy(@PathVariable("id") Long id, HttpServletRequest request) {
        HabAppUser user = PrincipalUtil.getAppUser(request);
        habHabitService.copyFromHabit(user.getId(), id);
        return getResponse(ApiResultConstants.SUCCESS, "", null);
    }

    /**
     * 根据习惯id，获得习惯详情
     * @return
     */
    @ApiOperation(value = "通过起始日期，频率类型，频率日期和坚持天数获得习惯结束日期")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/getHabitEndDate", method = RequestMethod.POST)
    public String getHabitEndDate(@RequestBody EndDateCond endDateCond) {
        return getResponse(ApiResultConstants.SUCCESS, "", HabitEndDateUtil.generateEndDate(endDateCond.getStartDate(), endDateCond.getFreqType(), endDateCond.getFreqDays(), endDateCond.getStickDays()));
    }
}