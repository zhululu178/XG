package com.letsup.habit.app.backend.dao.mapper;

import com.letsup.habit.app.backend.dao.entity.HabAppHabitClass;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;

@Resource
public interface HabAppHabitClassMapperExt extends HabAppHabitClassMapper {
    List<HabAppHabitClass> getByParentId(@Param("parentId") Long parentId);

    List<HabAppHabitClass> getAll();
}