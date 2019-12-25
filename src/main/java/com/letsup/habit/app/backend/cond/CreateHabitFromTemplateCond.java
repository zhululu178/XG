package com.letsup.habit.app.backend.cond;

import java.util.List;

public class CreateHabitFromTemplateCond {
    private Long memberId;//习惯的执行人
    private Integer stickDays;//坚持天数
    private List<Long> templates;//模板id列表

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getStickDays() {
        return stickDays;
    }

    public void setStickDays(Integer stickDays) {
        this.stickDays = stickDays;
    }

    public List<Long> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Long> templates) {
        this.templates = templates;
    }
}
