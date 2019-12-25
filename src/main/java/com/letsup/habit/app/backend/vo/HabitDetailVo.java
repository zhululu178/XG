package com.letsup.habit.app.backend.vo;

import java.util.List;

public class HabitDetailVo extends HabitSimpleVo {
    private String startDate;
    private String endDate;
    private Long class1;
    private Long class2;
    private String class1Name;
    private String class2Name;
    private List<HabitRemindVo> reminds;
    private String freqType;
    private String status;
    private List<String> freqDays;
    private String timer;

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

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
