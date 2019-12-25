package com.letsup.habit.app.backend.vo;

import java.util.List;

public class HabitTemplateClassVo {
    private String name;
    private List<HabitTemplateSimpleVo> templates;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HabitTemplateSimpleVo> getTemplates() {
        return templates;
    }

    public void setTemplates(List<HabitTemplateSimpleVo> templates) {
        this.templates = templates;
    }
}
