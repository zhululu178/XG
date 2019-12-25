package com.letsup.habit.app.backend.constants;

public enum MessageType {
    TEMPLATE("习惯模板");

    private String name;

    MessageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
