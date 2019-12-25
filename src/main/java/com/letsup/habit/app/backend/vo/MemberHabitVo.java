package com.letsup.habit.app.backend.vo;

import java.util.List;

public class MemberHabitVo {
    private String memberName;//成员名字，如果是儿童就是昵称，大人就是角色名称（妈妈，爸爸）
    private String sex;//性别
    private String avatar;//头像
    private int starAmount;//当日星星总数
    private int processingNum;//待完成任务数量
    private List<HabitSimpleVo> habits;//当日习惯列表

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getStarAmount() {
        return starAmount;
    }

    public void setStarAmount(int starAmount) {
        this.starAmount = starAmount;
    }

    public List<HabitSimpleVo> getHabits() {
        return habits;
    }

    public void setHabits(List<HabitSimpleVo> habits) {
        this.habits = habits;
    }

    public int getProcessingNum() {
        return processingNum;
    }

    public void setProcessingNum(int processingNum) {
        this.processingNum = processingNum;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
