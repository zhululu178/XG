package com.letsup.habit.app.backend.dao.mapper;

import com.letsup.habit.app.backend.dao.entity.HabAppHabitIcon;

import javax.annotation.Resource;
import java.util.List;

@Resource
public interface HabAppHabitIconMapperExt extends HabAppHabitIconMapper {
    List<HabAppHabitIcon> getAll();
}