package com.letsup.habit.app.backend.service;

import com.letsup.habit.app.backend.vo.HabitTemplateClassVo;
import com.letsup.habit.app.backend.vo.HabitTemplateVo;

import java.util.List;

public interface HabHabitTemplateService {
    /**
     * 根据成员id获得模板列表
     * @param memberId
     * @return
     */
    List<HabitTemplateClassVo> getByMemberId(Long memberId);

    /**
     * 根据tag获得模板列表
     * @param tag
     * @return
     */
    List<HabitTemplateClassVo> getByTag(String tag);

    /**
     * 根据多个tag获得模板列表
     * @param tags
     * @return
     */
    List<HabitTemplateClassVo> getByTags(List<String> tags);

    /**
     * 根据id获得模板详情
     * @param id
     * @return
     */
    HabitTemplateVo getById(Long id);
}
