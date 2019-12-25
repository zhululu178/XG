package com.letsup.habit.app.backend.constants;

/**
 * 习惯类型，计时，计次
 */
public enum HabitStatusEnum {
    PROCESSING("进行中"),
    GIVEUP("放弃"),
    FINISHED("完成");

    private String name;

    HabitStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}