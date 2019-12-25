package com.letsup.habit.app.backend.cond;

public class CreateMemberCond {
    private String name;//姓名
    private String sex;//性别
    private String birthDay;//生日
    private String familyRole;//家庭中的角色，(爸爸，妈妈）
    private String avatar;//头像(存在与app中)
    public String getFamilyRole() {
        return familyRole;
    }

    public void setFamilyRole(String familyRole) {
        this.familyRole = familyRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
