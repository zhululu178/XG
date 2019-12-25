package com.letsup.habit.app.backend.constants;

public enum  HabitLogResourceTypeEnum {
    PICTURE("图片"),
    VIDEO("视频"),
    AUDIO("音频");

    private String name;

    HabitLogResourceTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}