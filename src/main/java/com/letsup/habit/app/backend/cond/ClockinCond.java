package com.letsup.habit.app.backend.cond;

public class ClockinCond {
    private Long habitId;//习惯id
    private int bonus;//星星数量
    private int count;//计数量
    private String date;//打卡日期
    private String notDoToday;//今日不做

    public Long getHabitId() {
        return habitId;
    }

    public void setHabitId(Long habitId) {
        this.habitId = habitId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotDoToday() {
        return notDoToday;
    }

    public void setNotDoToday(String notDoToday) {
        this.notDoToday = notDoToday;
    }
}
