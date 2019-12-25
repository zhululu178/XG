package com.letsup.habit.app.backend.constants;

/**
 * 家庭成员角色常量类
 */
public enum FamilyMemberRoleEnum {
    FATHER("爸爸"),
    MOTHER("妈妈"),
    KID("宝贝");

    private String name;

    FamilyMemberRoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}