package com.letsup.habit.app.backend.service;

import com.letsup.habit.app.backend.base.GlobalException;
import com.letsup.habit.app.backend.cond.ClockinCond;
import com.letsup.habit.app.backend.cond.CreateHabitLogCond;
import com.letsup.habit.app.backend.vo.HabitClockinVo;
import com.letsup.habit.app.backend.vo.HabitDailyRecordVo;

public interface HabHabitClockinService {
    /**
     * 打卡
     * @param clockinCond
     * @param userId
     */
    HabitClockinVo clockIn(Long userId, ClockinCond clockinCond) throws GlobalException;

    /**
     * 今日不做
     * @param userId
     * @param createHabitLogCond
     */
    void notDoToday(Long userId, CreateHabitLogCond createHabitLogCond) throws GlobalException;

    /**
     * 取消打卡
     * @param userId
     * @param habitId
     */
    HabitDailyRecordVo cancelClockIn(Long userId, Long habitId) throws GlobalException;

    /**
     * 根据习惯id和打卡日期，获得打卡详情信息
     * @param habitId
     * @param clockinDate
     */
    HabitDailyRecordVo getByHabitId(Long habitId, String clockinDate) throws GlobalException;
}