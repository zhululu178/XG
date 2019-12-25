package com.letsup.habit.app.backend.vo;

public class TokenVo {
    //访问token
    private String access_token;
    //刷新访问token的token
    private String refresh_token;

    public String getAccess_token() {
        return access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
