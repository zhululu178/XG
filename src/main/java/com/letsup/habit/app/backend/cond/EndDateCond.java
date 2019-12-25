package com.letsup.habit.app.backend.cond;

/**
 * 结束日期生产条件
 */
public class EndDateCond {
    private String startDate;
    private String freqType;
    private String freqDays;
    private int stickDays;//坚持天数

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFreqType() {
        return freqType;
    }

    public void setFreqType(String freqType) {
        this.freqType = freqType;
    }

    public String getFreqDays() {
        return freqDays;
    }

    public void setFreqDays(String freqDays) {
        this.freqDays = freqDays;
    }

    public int getStickDays() {
        return stickDays;
    }

    public void setStickDays(int stickDays) {
        this.stickDays = stickDays;
    }
}
