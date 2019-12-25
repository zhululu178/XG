package com.letsup.habit.app.backend.service;

import com.letsup.habit.app.backend.base.GlobalException;
import com.letsup.habit.app.backend.cond.CreateHabitCond;
import com.letsup.habit.app.backend.cond.CreateHabitFromTemplateCond;
import com.letsup.habit.app.backend.vo.*;

import java.util.List;

public interface HabHabitService {
    /**
     * 根据模板创建习惯
     * @param createHabitFromTemplateCond
     */
    List<HabitRemindVo> addByTemplate(Long familyId, Long userId, CreateHabitFromTemplateCond createHabitFromTemplateCond) throws GlobalException;

    /**
     * 创建习惯
     * @param createHabitCond
     */
    void add(Long familyId, Long userId, CreateHabitCond createHabitCond) throws GlobalException;

    /**
     * 习惯删除
     * @param id
     * @param userId
     */
    void delete(Long id, Long userId) throws GlobalException;

    /**
     * 习惯放弃
     * @param id
     * @param userId
     */
    void giveUp(Long id, Long userId) throws GlobalException;

    /**
     * 修改习惯
     * @param createHabitCond
     */
    void modify(Long familyId, Long userId, CreateHabitCond createHabitCond) throws GlobalException;

    /**
     * 根据习惯id，获得习惯详情
     * @param id
     * @return
     */
    HabitDetailVo getById(Long id);

    /**
     * 通过家庭id和今天以后的日期获得成员与习惯的列表
     * @param familyId
     * @return
     */
    List<MemberHabitVo> getFromTodayOnHabitByFamilyId(Long familyId, String date);

    /**
     * 通过家庭id和今天以前的日期获得成员与习惯的列表
     * @param familyId
     * @return
     */
    List<MemberHabitVo> getBeforeTodayHabitByFamilyId(Long familyId, String date);

    /**
     * 根据家庭id和状态分页查询习惯列表
     * @param familyId
     * @param status
     * @param pageIndex
     * @param pageCount
     * @return
     */
    List<MemberHabitListVo> getPageByCond(Long familyId, String status, Integer pageIndex, Integer pageCount);

    /**
     * 如果结束日期已经到，但是还没有结束的习惯需要结束掉
     * 结束条件：只能结束1天前还正在进行的习惯
     */
    void finishExpiredHabit();

    /**
     * 根据习惯id，再来一次习惯
     * @param habitId
     */
    void copyFromHabit(Long userId, Long habitId);
}