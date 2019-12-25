package com.letsup.habit.app.backend.app;

import com.letsup.habit.app.backend.base.BaseController;
import com.letsup.habit.app.backend.base.GlobalException;
import com.letsup.habit.app.backend.cond.CreateMemberCond;
import com.letsup.habit.app.backend.cond.CreateMembersCond;
import com.letsup.habit.app.backend.constants.ApiResultConstants;
import com.letsup.habit.app.backend.dao.entity.HabAppUser;
import com.letsup.habit.app.backend.service.HabFamilyMemberService;
import com.letsup.habit.app.backend.util.PrincipalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(description = "家庭成员功能")
@RequestMapping("/app/familymember")
public class FamilyMemberApp extends BaseController {
    @Autowired
    private HabFamilyMemberService habFamilyMemberService;

    /**
     * 新增成人家庭成员
     * @return
     */
    @ApiOperation(value = "创建成人家庭成员")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/createAdult", method = RequestMethod.POST)
    public String createAdult(@RequestBody CreateMemberCond createMemberCond, HttpServletRequest request) {
        HabAppUser user = PrincipalUtil.getAppUser(request);
        try {
            return getResponse(ApiResultConstants.SUCCESS, "", habFamilyMemberService.addAdultMember(user.getFamilyId(), user.getId(), createMemberCond));
        }  catch (GlobalException e) {
            this.logger.debug("创建成人家庭成员失败. " + e.getMessage());
            e.printStackTrace();
            return this.getResponse(e);
        }
    }

    /**
     * 新增儿童家庭成员
     * @return
     */
    @ApiOperation(value = "创建儿童家庭成员")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/createChild", method = RequestMethod.POST)
    public String createChild(@RequestBody CreateMembersCond createMembersCond, HttpServletRequest request) {
        HabAppUser user = PrincipalUtil.getAppUser(request);
        try {
            return getResponse(ApiResultConstants.SUCCESS, "", habFamilyMemberService.addChildMember(user.getFamilyId(), user.getId(), createMembersCond.getMembers()));
        }  catch (GlobalException e) {
            this.logger.debug("创建儿童家庭成员失败. " + e.getMessage());
            e.printStackTrace();
            return this.getResponse(e);
        }
    }

    /**
     * 获得家庭中的成员列表
     * @return
     */
    @ApiOperation(value = "获得家庭中的成员列表")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/getMembers", method = RequestMethod.GET)
    public String getMembers(HttpServletRequest request) {
        HabAppUser user = PrincipalUtil.getAppUser(request);
        try {
            return getResponse(ApiResultConstants.SUCCESS, "", habFamilyMemberService.getMemberRoleByFamilyId(user.getFamilyId()));
        }  catch (GlobalException e) {
            this.logger.debug("获得家庭中的成员列表失败. " + e.getMessage());
            e.printStackTrace();
            return this.getResponse(e);
        }
    }
}
