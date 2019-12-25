package com.letsup.habit.app.backend.vo;

import java.io.Serializable;

/**
 * 习惯打卡记录Vo
 */
public class HabitClockinRecordVo implements Serializable {
    private Long dailyRecordId;
    private Integer bonus;
    private Integer count;

    public Long getDailyRecordId() {
        return dailyRecordId;
    }

    public void setDailyRecordId(Long dailyRecordId) {
        this.dailyRecordId = dailyRecordId;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
