package com.letsup.habit.app.backend.vo;

import java.util.List;

public class HabitSimpleVo {
    private Long id;
    private Long executor;
    private String title;
    private String iconUrl;
    private String iconColor;
    private String hasLog;
    private Integer stickDays;//目标坚持天数
    private Integer stickedDays;//已坚持天数
    private Integer dailyCount;//每天的计数数量
    private Integer eachTime;//每计数时间
    private String type;//习惯类型，时间还是计次
    private int actualCount;//实际完成的计数量
    private int bonus;//实际星星数量星星数量
    private int collectedBonus;//已被收集的星星数量
    private List<String> counter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getIconColor() {
        return iconColor;
    }

    public void setIconColor(String iconColor) {
        this.iconColor = iconColor;
    }

    public Integer getStickDays() {
        return stickDays;
    }

    public void setStickDays(Integer stickDays) {
        this.stickDays = stickDays;
    }

    public Integer getStickedDays() {
        return stickedDays;
    }

    public void setStickedDays(Integer stickedDays) {
        this.stickedDays = stickedDays;
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

    public Long getExecutor() {
        return executor;
    }

    public void setExecutor(Long executor) {
        this.executor = executor;
    }

    public String getHasLog() {
        return hasLog;
    }

    public void setHasLog(String hasLog) {
        this.hasLog = hasLog;
    }

    public int getActualCount() {
        return actualCount;
    }

    public void setActualCount(int actualCount) {
        this.actualCount = actualCount;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public int getCollectedBonus() {
        return collectedBonus;
    }

    public void setCollectedBonus(int collectedBonus) {
        this.collectedBonus = collectedBonus;
    }

    public List<String> getCounter() {
        return counter;
    }

    public void setCounter(List<String> counter) {
        this.counter = counter;
    }
}
