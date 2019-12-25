package com.letsup.habit.app.backend.vo;

public class MemberHabitListVo extends HabitSimpleVo {
    private String memberName;
    private String memberRole;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(String memberRole) {
        this.memberRole = memberRole;
    }
}
