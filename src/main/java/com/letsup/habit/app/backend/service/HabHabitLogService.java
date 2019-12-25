package com.letsup.habit.app.backend.service;

import com.letsup.habit.app.backend.cond.CreateHabitLogCond;
import com.letsup.habit.app.backend.dao.entity.HabAppHabitLog;
import com.letsup.habit.app.backend.vo.HabitLogVo;

public interface HabHabitLogService {
    /**
     * 新增习惯日志
     * @param createHabitLogCond
     */
    void add(Long userId, CreateHabitLogCond createHabitLogCond);

    /**
     * 只新增习惯日志
     * @param createHabitLogCond
     */
    HabAppHabitLog addOnlyLog(Long userId, CreateHabitLogCond createHabitLogCond);

    /**
     * 修改习惯日志
     * @param createHabitLogCond
     */
    void modify(Long userId, CreateHabitLogCond createHabitLogCond);

    /**
     * 删除习惯日志
     * @param id
     */
    void delete(Long userId, Long id);

    /**
     * 根据id获得习惯日志详情
     * @param id
     */
    HabitLogVo getById(Long id);

    /**
     * 根据习惯id和日期获得最新一条日志详情
     * @param habitId
     */
    HabitLogVo getLastLogByHabitId(Long habitId, String logDate);
}
