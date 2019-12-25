package com.letsup.habit.app.backend.base;


import com.letsup.habit.app.backend.constants.ApiResultConstants;

public class GlobalException extends Exception {
    private Integer code;

    public GlobalException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public GlobalException(ApiResultConstants.ExceptionMessage em) {
        super(em.getMessage());
        this.code = em.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
