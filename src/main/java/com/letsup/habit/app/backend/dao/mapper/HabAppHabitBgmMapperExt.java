package com.letsup.habit.app.backend.dao.mapper;

import com.letsup.habit.app.backend.dao.entity.HabAppHabitBgm;

import javax.annotation.Resource;
import java.util.List;

@Resource
public interface HabAppHabitBgmMapperExt extends HabAppHabitBgmMapper {
    List<HabAppHabitBgm> getAll();
}