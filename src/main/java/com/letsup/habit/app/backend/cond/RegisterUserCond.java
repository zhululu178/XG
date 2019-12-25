package com.letsup.habit.app.backend.cond;

/**
 * 用户注册
 */
public class RegisterUserCond {
    private String imei;//手机设备号
    private String phone;//手机
    private String password;//密码

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
