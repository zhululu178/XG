package com.letsup.habit.app.backend.service.impl;

import com.letsup.habit.app.backend.base.GlobalException;
import com.letsup.habit.app.backend.cond.CreateMemberCond;
import com.letsup.habit.app.backend.constants.BooleanConstants;
import com.letsup.habit.app.backend.constants.FamilyMemberRoleEnum;
import com.letsup.habit.app.backend.constants.HabConstants;
import com.letsup.habit.app.backend.dao.cache.HabAppFamilyMemberMapperCache;
import com.letsup.habit.app.backend.dao.cache.HabAppUserMapperCache;
import com.letsup.habit.app.backend.dao.entity.HabAppFamilyMember;
import com.letsup.habit.app.backend.dao.entity.HabAppFamilyMemberExample;
import com.letsup.habit.app.backend.dao.entity.HabAppUser;
import com.letsup.habit.app.backend.service.HabFamilyMemberService;
import com.letsup.habit.app.backend.util.DateTimeUtil;
import com.letsup.habit.app.backend.vo.FamilyVo;
import com.letsup.habit.app.backend.vo.MemberVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.letsup.habit.app.backend.constants.ApiResultConstants.ExceptionMessage.*;

/**
 * 家庭成员服务类
 */
@Service
public class HabFamilyMemberServiceImpl implements HabFamilyMemberService {
    @Autowired
    private HabAppUserMapperCache habAppUserMapperExt;
    @Autowired
    private HabAppFamilyMemberMapperCache habAppFamilyMemberMapperExt;

    @Override
    public HabAppFamilyMember addAdultMember(Long familyId, Long userId, CreateMemberCond createMemberCond) throws GlobalException{
        HabAppFamilyMemberExample example = new HabAppFamilyMemberExample();
        example.createCriteria().andFamilyIdEqualTo(familyId).andRoleEqualTo(createMemberCond.getFamilyRole()).andIsDeletedEqualTo("n");
        List<HabAppFamilyMember> members = habAppFamilyMemberMapperExt.selectByExample(example);
        if(members.size() > 0) {
            throw new GlobalException(FAMILY_MEMBER_ROLE_EXISTS);
        }

        HabAppFamilyMember member = new HabAppFamilyMember();
        member.setIsChild(BooleanConstants.NO);
        this.addMember(userId, familyId, member, createMemberCond);

        HabAppUser user = habAppUserMapperExt.selectByPrimaryKey(userId);
        user.setMemberId(member.getId());
        this.habAppUserMapperExt.updateByPrimaryKeySelective(user);

        return member;
    }

    private void checkChild(Long familyId, List<CreateMemberCond> createMemberConds) throws GlobalException {
        HabAppFamilyMemberExample example = new HabAppFamilyMemberExample();
        example.createCriteria().andRoleEqualTo(FamilyMemberRoleEnum.KID.name()).andIsDeletedEqualTo("n").andFamilyIdEqualTo(familyId);
        int kidNum = habAppFamilyMemberMapperExt.countByExample(example);
        if(kidNum + createMemberConds.size() > HabConstants.KID_NUM) {
            throw new GlobalException(FAMILY_KIDS_NUM_EXCEED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HabAppFamilyMember addChildMember(Long familyId, Long userId, List<CreateMemberCond> createMemberConds) throws GlobalException{
        HabAppFamilyMember member = null;
        if(createMemberConds != null && createMemberConds.size() > 0) {
            checkChild(familyId, createMemberConds);
            for(CreateMemberCond createMemberCond : createMemberConds) {
                HabAppFamilyMemberExample example = new HabAppFamilyMemberExample();
                example.createCriteria().andFamilyIdEqualTo(familyId).andNameEqualTo(createMemberCond.getName()).andIsDeletedEqualTo("n");
                List<HabAppFamilyMember> members = habAppFamilyMemberMapperExt.selectByExample(example);
                if(members.size() > 0) {
                    throw new GlobalException(FAMILY_MEMBER_NAME_EXISTS);
                }

                member = new HabAppFamilyMember();
                member.setIsChild(BooleanConstants.YES);
                this.addMember(userId, familyId, member, createMemberCond);
            }
        }

        return member;
    }

    private void addMember(Long userId, Long familyId, HabAppFamilyMember member, CreateMemberCond createMemberCond) {
        member.setVersion(0l);
        member.setFamilyId(familyId);
        member.setBonus(0);
        member.setCollectBonus(0);
        member.setSex(createMemberCond.getSex());
        if(StringUtils.isNotEmpty(createMemberCond.getBirthDay())) {
            member.setBirthDay(DateTimeUtil.strToDate(createMemberCond.getBirthDay()));
        }
        member.setName(createMemberCond.getName());
        member.setAvatar(createMemberCond.getAvatar());
        member.setRole(createMemberCond.getFamilyRole());
        member.setCreator(userId.toString());
        member.setModifier(member.getCreator());
        this.habAppFamilyMemberMapperExt.insertSelective(member);
    }

    @Override
    public FamilyVo getMemberRoleByFamilyId(Long familyId) throws GlobalException {
        FamilyVo familyVo = new FamilyVo();
        HabAppFamilyMemberExample example = new HabAppFamilyMemberExample();
        example.createCriteria().andFamilyIdEqualTo(familyId).andIsDeletedEqualTo("n");
        List<HabAppFamilyMember> members = habAppFamilyMemberMapperExt.selectByExample(example);
        if(members.size() > 0) {
            MemberVo memberVo;
            for(HabAppFamilyMember m : members) {
                memberVo = this.convertToVo(m);
                if(BooleanConstants.YES.equals(m.getIsChild())) {
                    memberVo.setChild(true);
                    familyVo.getKids().add(memberVo);
                } else {
                    familyVo.getAdults().add(memberVo);
                }
                familyVo.getAll().add(this.convertToVo(m));//转成json的时候，如果是引用，会出错。[{"$ref":"$.data.adults[0]"},{"$ref":"$.data.adults[1]"},
            }
        }
        return familyVo;
    }

    private MemberVo convertToVo(HabAppFamilyMember m) {
        MemberVo memberVo = new MemberVo();
        BeanUtils.copyProperties(m, memberVo);
        if(m.getBirthDay() != null) {
            memberVo.setBirthDay(DateTimeUtil.dateToStr(m.getBirthDay()));
        }
        if(BooleanConstants.YES.equals(m.getIsChild())) {
            memberVo.setChild(true);
        }
        return memberVo;
    }
}
