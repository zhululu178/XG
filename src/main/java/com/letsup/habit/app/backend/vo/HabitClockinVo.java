package com.letsup.habit.app.backend.vo;

import java.util.List;

/**
 * 打卡返回的VO
 */
public class HabitClockinVo {
    private Long habitId;
    private int bonus;
    private int actualCount;
    private String status;
    private List<HabitRemindVo> reminds;

    public Long getHabitId() {
        return habitId;
    }

    public void setHabitId(Long habitId) {
        this.habitId = habitId;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public int getActualCount() {
        return actualCount;
    }

    public void setActualCount(int actualCount) {
        this.actualCount = actualCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<HabitRemindVo> getReminds() {
        return reminds;
    }

    public void setReminds(List<HabitRemindVo> reminds) {
        this.reminds = reminds;
    }
}
