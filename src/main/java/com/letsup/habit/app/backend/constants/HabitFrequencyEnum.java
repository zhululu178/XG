package com.letsup.habit.app.backend.constants;

/**
 * 习惯的频率类型常量
 */
public enum HabitFrequencyEnum {
    F1("每天"),
    F7("每周"),
    F31("每月"),
    FX("每X天");

    private String name;

    HabitFrequencyEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}