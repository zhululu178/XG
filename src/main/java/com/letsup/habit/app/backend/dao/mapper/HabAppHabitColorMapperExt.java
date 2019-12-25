package com.letsup.habit.app.backend.dao.mapper;

import com.letsup.habit.app.backend.dao.entity.HabAppHabitColor;

import javax.annotation.Resource;
import java.util.List;

@Resource
public interface HabAppHabitColorMapperExt extends HabAppHabitColorMapper {
    List<HabAppHabitColor> getAll();
}