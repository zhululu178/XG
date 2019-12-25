package com.letsup.habit.app.backend.service;

import com.letsup.habit.app.backend.base.GlobalException;
import com.letsup.habit.app.backend.cond.AppLoginCond;
import com.letsup.habit.app.backend.cond.RegisterUserCond;
import com.letsup.habit.app.backend.dao.entity.HabAppUser;
import com.letsup.habit.app.backend.vo.UserVo;

public interface HabUserService {
    HabAppUser getByPhone(String username);

    HabAppUser getByIMEI(String imei);

    UserVo getById(Long id);

    /**
     * 新增/注册用户信息
     * @param registerUserCond
     * @return
     */
    HabAppUser add(RegisterUserCond registerUserCond);

    /**
     * 用户登录时，密码验证
     * @param appLoginCond
     * @return
     */
    HabAppUser loginCheck(AppLoginCond appLoginCond) throws GlobalException;
}
