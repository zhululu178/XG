package com.letsup.habit.app.backend.dao.mapper;

import com.letsup.habit.app.backend.dao.entity.HabAppHabitClockinRecord;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;

@Resource
public interface HabAppHabitClockinRecordMapperExt extends HabAppHabitClockinRecordMapper {
    /**
     * 根据每日记录id获得最后打卡记录
     * @param habitDailyRecordId
     * @return
     */
    HabAppHabitClockinRecord getLastByDailyRecordId(@Param("habitDailyRecordId")Long habitDailyRecordId);
}