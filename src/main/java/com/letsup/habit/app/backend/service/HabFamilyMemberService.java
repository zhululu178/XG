package com.letsup.habit.app.backend.service;

import com.letsup.habit.app.backend.base.GlobalException;
import com.letsup.habit.app.backend.cond.CreateMemberCond;
import com.letsup.habit.app.backend.dao.entity.HabAppFamilyMember;
import com.letsup.habit.app.backend.vo.FamilyVo;

import java.util.List;

public interface HabFamilyMemberService {
    /**
     * 为指定家庭新增成人成员
     * @param familyId
     * @param userId
     * @param createMemberCond
     */
    HabAppFamilyMember addAdultMember(Long familyId, Long userId, CreateMemberCond createMemberCond) throws GlobalException;

    /**
     * 为指定家庭新增儿童成员
     * @param familyId
     * @param createMemberConds
     */
    HabAppFamilyMember addChildMember(Long familyId, Long userId, List<CreateMemberCond> createMemberConds) throws GlobalException;

    /**
     * 根据家庭id获得成员家庭角色列表信息
     * @param familyId
     */
    FamilyVo getMemberRoleByFamilyId(Long familyId) throws GlobalException;
}
