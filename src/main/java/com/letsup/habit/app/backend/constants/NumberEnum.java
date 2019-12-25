package com.letsup.habit.app.backend.constants;

public enum NumberEnum {
    一("1"),
    二("2"),
    三("3"),
    四("4"),
    五("5"),
    六("6"),
    七("7");

    private String name;

    NumberEnum(String name) {
        this.name = name;
    }

    public static NumberEnum getByName(String name) {
        for(NumberEnum num : NumberEnum.values()) {
            if(num.getName().equals(name)) {
                return num;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
