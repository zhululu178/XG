package com.letsup.habit.app.backend.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.letsup.habit.app.backend.constants.FamilyMemberRoleEnum;
import com.letsup.habit.app.backend.constants.HabitFrequencyEnum;
import com.letsup.habit.app.backend.constants.NumberEnum;
import com.letsup.habit.app.backend.dao.cache.HabAppFamilyMemberMapperCache;
import com.letsup.habit.app.backend.dao.entity.*;
import com.letsup.habit.app.backend.dao.mapper.HabAppHabitClassMapperExt;
import com.letsup.habit.app.backend.dao.mapper.HabAppHabitTemplateMapperExt;
import com.letsup.habit.app.backend.dao.mapper.HabAppHabitTemplateTagMapperExt;
import com.letsup.habit.app.backend.service.HabHabitTemplateService;
import com.letsup.habit.app.backend.util.DateTimeUtil;
import com.letsup.habit.app.backend.vo.HabitRemindVo;
import com.letsup.habit.app.backend.vo.HabitTemplateClassVo;
import com.letsup.habit.app.backend.vo.HabitTemplateSimpleVo;
import com.letsup.habit.app.backend.vo.HabitTemplateVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HabHabitTemplateServiceImpl implements HabHabitTemplateService {
    @Autowired
    private HabAppHabitClassMapperExt habAppHabitClassMapperExt;
    @Autowired
    private HabAppFamilyMemberMapperCache habAppFamilyMemberMapperExt;
    @Autowired
    private HabAppHabitTemplateMapperExt habAppHabitTemplateMapperExt;
    @Autowired
    private HabAppHabitTemplateTagMapperExt habAppHabitTemplateTagMapperExt;

    @Override
    public List<HabitTemplateClassVo> getByMemberId(Long memberId) {
        HabAppFamilyMember member = habAppFamilyMemberMapperExt.selectByPrimaryKey(memberId);
        if(member != null) {
            List<String> tags = new ArrayList<>();
            if(FamilyMemberRoleEnum.KID.name().equals(member.getRole())) {
                int age = DateTimeUtil.getAge(member.getBirthDay());
                HabAppHabitTemplateTagExample example = new HabAppHabitTemplateTagExample();
                example.createCriteria().andIsDeletedEqualTo("n").andMinValueLessThanOrEqualTo(age).andMaxValueGreaterThanOrEqualTo(age);
                List<HabAppHabitTemplateTag> tagsList = habAppHabitTemplateTagMapperExt.selectByExample(example);
                for(HabAppHabitTemplateTag tag : tagsList) {
                    tags.add(tag.getName());
                }
            } else {
                tags.add(FamilyMemberRoleEnum.valueOf(member.getRole()).getName());
            }
            return this.getByTags(tags);
        }
        return null;
    }

    @Override
    public List<HabitTemplateClassVo> getByTag(String tag) {
        if(StringUtils.isNotEmpty(tag)) {
            List tagList = new ArrayList<>(1);
            tagList.add(tag);
            return this.getByTags(tagList);
        }
        return null;
    }

    @Override
    public List<HabitTemplateClassVo> getByTags(List<String> tags) {
        return this.habAppHabitTemplateMapperExt.getByTags(tags);
    }

    /**
     * 根据习惯类型获得名称
     * @param habitClassId
     * @return
     */
    private String getHabitClassName(Long habitClassId) {
        HabAppHabitClass habAppHabitClass = this.habAppHabitClassMapperExt.selectByPrimaryKey(habitClassId);
        if(habAppHabitClass != null) {
            return habAppHabitClass.getName();
        }
        return "";
    }

    @Override
    public HabitTemplateVo getById(Long id) {
        HabAppHabitTemplate template = this.habAppHabitTemplateMapperExt.selectByPrimaryKey(id);
        HabitTemplateVo vo = new HabitTemplateVo();
        BeanUtils.copyProperties(template, vo);
        if(StringUtils.isNotEmpty(template.getRemind())) {
            JSONArray.parseArray(template.getRemind(), HabitRemindVo.class);
            vo.setReminds(JSONArray.parseArray(template.getRemind(), HabitRemindVo.class));//提醒信息列表
        }
        if(StringUtils.isNotEmpty(template.getFreqDays())) {
            vo.setFreqDays(Arrays.asList(template.getFreqDays().split(",")));//频率天数列表
        }
        if(template.getClass1() != null) {
            vo.setClass1Name(this.getHabitClassName(template.getClass1()));
        }
        if(template.getClass2() != null) {
            vo.setClass2Name(this.getHabitClassName(template.getClass2()));
        }
        if(HabitFrequencyEnum.F1.name().equals(template.getFreqType())) {//频率说明设置
            if(vo.getFreqDays() != null) {
                if(vo.getFreqDays().size() == 7) {
                    vo.setFrequency("每天");
                } else {
                    vo.setFrequency("周");
                    for(String day : vo.getFreqDays()) {
                        vo.setFrequency(vo.getFrequency() + NumberEnum.getByName(day).name() + ",");
                    }
                    if(vo.getFrequency().lastIndexOf(",") == vo.getFrequency().length() - 1) {
                        vo.setFrequency(vo.getFrequency().substring(0, vo.getFrequency().length() - 1));
                    }
                }
            }
        } else {
            vo.setFrequency(HabitFrequencyEnum.valueOf(template.getFreqType()).name() + template.getFreqDays() + "天");
        }
        //当日目标
        if(template.getDailyCount() != null) {
            vo.setDailyTarget("每日计数" + template.getDailyCount());
        }
        if(template.getEachTime() != null) {
            vo.setDailyTarget(vo.getDailyTarget() + " 每日" + template.getEachTime() + "分钟");
        }
        return vo;
    }
}
