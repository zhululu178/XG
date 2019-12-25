package com.letsup.habit.app.backend.vo;

import java.util.List;

public class HabitClassVo {
    private Long id;
    private String name;
    private String description;
    private String icon;
    private List<HabitClassVo> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<HabitClassVo> getChildren() {
        return children;
    }

    public void setChildren(List<HabitClassVo> children) {
        this.children = children;
    }
}
