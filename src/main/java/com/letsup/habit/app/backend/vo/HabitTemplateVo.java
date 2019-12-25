package com.letsup.habit.app.backend.vo;

import java.util.List;

public class HabitTemplateVo extends HabitTemplateSimpleVo{
    private String description;
    private String type;
    private Integer stickDays;
    private Integer dailyCount;
    private Integer eachTime;
    private Long class1;
    private String class1Name;
    private Long class2;
    private String class2Name;
    private List<HabitRemindVo> reminds;
    private String freqType;
    private List<String> freqDays;
    private String dailyTarget = "";
    private String frequency = "";

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getClass1Name() {
        return class1Name;
    }

    public void setClass1Name(String class1Name) {
        this.class1Name = class1Name;
    }

    public String getClass2Name() {
        return class2Name;
    }

    public void setClass2Name(String class2Name) {
        this.class2Name = class2Name;
    }

    public String getDailyTarget() {
        return dailyTarget;
    }

    public void setDailyTarget(String dailyTarget) {
        this.dailyTarget = dailyTarget;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
