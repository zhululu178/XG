package com.letsup.habit.app.backend.app;

import com.letsup.habit.app.backend.base.BaseController;
import com.letsup.habit.app.backend.constants.ApiResultConstants;
import com.letsup.habit.app.backend.constants.FamilyMemberRoleEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(description = "习惯模板tag")
@RequestMapping("/app/tag")
public class HabitRecommandTagApp extends BaseController {

    /**
     * 根据家庭成员角色获得tag列表
     * @return
     */
    @ApiOperation(value = "根据家庭成员角色获得tag列表")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/getTagByRole", method = RequestMethod.GET)
    public String getTemplateByTag(@RequestParam("role") String role, HttpServletRequest request) {
        List<String> list = new ArrayList<>();
        list.add("推荐");
        if(StringUtils.isNotEmpty(role)) {
            if(FamilyMemberRoleEnum.KID.name().equals(role)) {
                list.add("小班");
                list.add("中班");
                list.add("大班");
                list.add("一年级");
                list.add("二年级");
                list.add("三年级");
                list.add("四年级");
                list.add("五年级");
                list.add("六年级");
            } else {
                list.add(FamilyMemberRoleEnum.valueOf(role).getName());
            }
        }
        return getResponse(ApiResultConstants.SUCCESS, "", list);
    }
}