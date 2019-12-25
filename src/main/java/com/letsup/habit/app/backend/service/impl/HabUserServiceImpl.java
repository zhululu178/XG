package com.letsup.habit.app.backend.service.impl;

import com.letsup.habit.app.backend.base.GlobalException;
import com.letsup.habit.app.backend.cond.AppLoginCond;
import com.letsup.habit.app.backend.cond.RegisterUserCond;
import com.letsup.habit.app.backend.constants.ApiResultConstants;
import com.letsup.habit.app.backend.constants.BooleanConstants;
import com.letsup.habit.app.backend.dao.cache.HabAppFamilyMapperCache;
import com.letsup.habit.app.backend.dao.cache.HabAppUserMapperCache;
import com.letsup.habit.app.backend.dao.entity.*;
import com.letsup.habit.app.backend.dao.mapper.HabAppRoleMapperExt;
import com.letsup.habit.app.backend.dao.mapper.HabAppRoleUserReMapperExt;
import com.letsup.habit.app.backend.service.HabUserService;
import com.letsup.habit.app.backend.util.MD5Util;
import com.letsup.habit.app.backend.util.UUIDUtil;
import com.letsup.habit.app.backend.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户查看服务类
 */
@Service
public class HabUserServiceImpl implements HabUserService {
    @Autowired
    private HabAppUserMapperCache habAppUserMapperExt;
    @Autowired
    private HabAppFamilyMapperCache habAppFamilyMapperExt;
    @Autowired
    private HabAppRoleMapperExt habAppRoleMapperExt;
    @Autowired
    private HabAppRoleUserReMapperExt habAppRoleUserReMapperExt;

    @Override
    public HabAppUser getByPhone(String phone) {
        HabAppUserExample example = new HabAppUserExample();
        example.createCriteria().andPhoneEqualTo(phone);
        List<HabAppUser> users = habAppUserMapperExt.selectByExample(example);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public HabAppUser getByIMEI(String imei) {
        HabAppUserExample example = new HabAppUserExample();
        example.createCriteria().andImeiEqualTo(imei);
        List<HabAppUser> users = habAppUserMapperExt.selectByExample(example);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public UserVo getById(Long id) {
        UserVo userVo = null;
        HabAppUser user = habAppUserMapperExt.selectByPrimaryKey(id);
        if(user != null) {
            userVo = new UserVo();
            userVo.setId(user.getId());
            userVo.setImei(user.getImei());
            if(user.getFamilyId() != null) {
                HabAppFamily family = habAppFamilyMapperExt.selectByPrimaryKey(user.getFamilyId());
                if(family != null) {
                    userVo.setFamilyId(family.getId());
                    userVo.setNewFamily(family.getIsNew());
                }
            }
        }
        return userVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HabAppUser add(RegisterUserCond registerUserCond) {
        //1. 家庭
        HabAppFamily appFamily = new HabAppFamily();
        appFamily.setIsNew(BooleanConstants.YES);
        appFamily.setNo(UUIDUtil.getUUID());
        this.habAppFamilyMapperExt.insertSelective(appFamily);
        //2. 用户
        HabAppUser appUser = new HabAppUser();
        appUser.setImei(registerUserCond.getImei());
        appUser.setPhone(registerUserCond.getPhone());
        appUser.setFamilyId(appFamily.getId());
        if(StringUtils.isNotEmpty(registerUserCond.getPassword())) {
            appUser.setPassword(MD5Util.encode(registerUserCond.getPassword()));
        }
        this.habAppUserMapperExt.insertSelective(appUser);
        //3. 用户默认角色
        HabAppRoleExample example = new HabAppRoleExample();
        example.createCriteria().andCodeEqualTo("ROLE_USER");
        List<HabAppRole> roleList = habAppRoleMapperExt.selectByExample(example);
        if(roleList != null && roleList.size() > 0) {
            HabAppRoleUserRe rur = new HabAppRoleUserRe();
            rur.setUserId(appUser.getId());
            rur.setRoleId(roleList.get(0).getId());
            this.habAppRoleUserReMapperExt.insertSelective(rur);
        }
        return appUser;
    }

    @Override
    public HabAppUser loginCheck(AppLoginCond appLoginCond) throws GlobalException {
        if(StringUtils.isEmpty(appLoginCond.getUsername())) {//用户名
            throw new GlobalException(ApiResultConstants.ExceptionMessage.LOGIN_USERNAME_REQUIRED.getCode(),
                    ApiResultConstants.ExceptionMessage.LOGIN_USERNAME_REQUIRED.getMessage());
        }
        if(StringUtils.isEmpty(appLoginCond.getPassword())) {//密码
            throw new GlobalException(ApiResultConstants.ExceptionMessage.LOGIN_PASSWORD_REQUIRED.getCode(),
                    ApiResultConstants.ExceptionMessage.LOGIN_PASSWORD_REQUIRED.getMessage());
        }
        return this.habAppUserMapperExt.getUserByPhoneAndPassword(appLoginCond.getUsername(), MD5Util.encode(appLoginCond.getPassword()));
    }
}
