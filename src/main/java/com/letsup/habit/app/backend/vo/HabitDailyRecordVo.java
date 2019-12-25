package com.letsup.habit.app.backend.vo;

import java.util.List;

public class HabitDailyRecordVo {
    private Long habitId;//习惯id
    private String habitTitle;//习惯标题
    private int bonus;//星星数量
    private int collectedBonus;//已收集星星数量
    private int count;//计数量
    private int dailyCount;//每天的计数数量
    private int eachTime;//每计数时间
    private String type;//习惯类型，时间还是计次
    private List<String> counter;
    private String timer;
    private String notDoToday;//今日不做

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCollectedBonus() {
        return collectedBonus;
    }

    public void setCollectedBonus(int collectedBonus) {
        this.collectedBonus = collectedBonus;
    }

    public Integer getDailyCount() {
        return dailyCount;
    }

    public void setDailyCount(Integer dailyCount) {
        this.dailyCount = dailyCount;
    }

    public Integer getEachTime() {
        return eachTime;
    }

    public void setEachTime(Integer eachTime) {
        this.eachTime = eachTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHabitTitle() {
        return habitTitle;
    }

    public void setHabitTitle(String habitTitle) {
        this.habitTitle = habitTitle;
    }

    public void setDailyCount(int dailyCount) {
        this.dailyCount = dailyCount;
    }

    public void setEachTime(int eachTime) {
        this.eachTime = eachTime;
    }

    public List<String> getCounter() {
        return counter;
    }

    public void setCounter(List<String> counter) {
        this.counter = counter;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String getNotDoToday() {
        return notDoToday;
    }

    public void setNotDoToday(String notDoToday) {
        this.notDoToday = notDoToday;
    }
}