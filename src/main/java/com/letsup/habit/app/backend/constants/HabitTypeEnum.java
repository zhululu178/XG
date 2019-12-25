package com.letsup.habit.app.backend.constants;

/**
 * 习惯类型，计时，计次
 */
public enum HabitTypeEnum {
    COUNT("计次"),
    TIME("计时");

    private String name;

    HabitTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}