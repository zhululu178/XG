package com.letsup.habit.app.backend.dao.mapper;

import com.letsup.habit.app.backend.dao.entity.HabAppHabitLog;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;

@Resource
public interface HabAppHabitLogMapperExt extends HabAppHabitLogMapper {
    /**
     * 根据习惯id和日期获得最新一条日志详情
     * @param habitId
     * @param logDate
     * @return
     */
    HabAppHabitLog getLastLogByHabitId(@Param("habitId") Long habitId, @Param("logDate") String logDate);
}