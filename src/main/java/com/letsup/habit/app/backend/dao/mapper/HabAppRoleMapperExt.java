package com.letsup.habit.app.backend.dao.mapper;

import com.letsup.habit.app.backend.dao.entity.HabAppRole;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;

@Resource
public interface HabAppRoleMapperExt extends HabAppRoleMapper {
    List<HabAppRole> getByUserId(@Param("userId") Long userId);
}