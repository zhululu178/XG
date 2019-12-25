package com.letsup.habit.app.backend.cond;

import com.letsup.habit.app.backend.vo.HabitRemindVo;

import java.util.List;

public class CreateHabitCond {
    private Long id;
    private Long executor;
    private String title;
    private String iconUrl;
    private String iconColor;
    private String type;
    private Integer stickDays;
    private String startDate;
    private String endDate;
    private Integer dailyCount;
    private Integer eachTime;
    private Long class1;
    private Long class2;
    private List<HabitRemindVo> reminds;
    private String freqType;
    private List<String> freqDays;
    private String timer;//计时器
    private List<String> counter;//计数器

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStickDays() {
        return stickDays;
    }

    public void setStickDays(Integer stickDays) {
        this.stickDays = stickDays;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public Long getClass1() {
        return class1;
    }

    public void setClass1(Long class1) {
        this.class1 = class1;
    }

    public Long getClass2() {
        return class2;
    }

    public void setClass2(Long class2) {
        this.class2 = class2;
    }

    public List<HabitRemindVo> getReminds() {
        return reminds;
    }

    public void setReminds(List<HabitRemindVo> reminds) {
        this.reminds = reminds;
    }

    public String getFreqType() {
        return freqType;
    }

    public void setFreqType(String freqType) {
        this.freqType = freqType;
    }

    public List<String> getFreqDays() {
        return freqDays;
    }

    public void setFreqDays(List<String> freqDays) {
        this.freqDays = freqDays;
    }

    public Long getExecutor() {
        return executor;
    }

    public void setExecutor(Long executor) {
        this.executor = executor;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public List<String> getCounter() {
        return counter;
    }

    public void setCounter(List<String> counter) {
        this.counter = counter;
    }
}
