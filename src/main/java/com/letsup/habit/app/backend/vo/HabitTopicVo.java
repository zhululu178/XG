package com.letsup.habit.app.backend.vo;

import java.util.List;

public class HabitTopicVo extends HabitTopicSimpleVo{
    private String title;
    private String description;
    private String content;
    private List<HabitTemplateClassVo> templateClasses;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<HabitTemplateClassVo> getTemplateClasses() {
        return templateClasses;
    }

    public void setTemplateClasses(List<HabitTemplateClassVo> templateClasses) {
        this.templateClasses = templateClasses;
    }
}
