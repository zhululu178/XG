package com.letsup.habit.app.backend.dao.mapper;

import com.letsup.habit.app.backend.dao.entity.HabAppUser;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;

@Resource
public interface HabAppUserMapperExt extends HabAppUserMapper {
    HabAppUser getUserByPhoneAndPassword(@Param("phone") String phone, @Param("password") String password);
}