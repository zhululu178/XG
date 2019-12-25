package com.letsup.habit.app.backend.vo;

public class HabitDailyBonusVo {
    private Long id;//每日星星数量
    private int bonus;//每日待收星星数量

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
}
