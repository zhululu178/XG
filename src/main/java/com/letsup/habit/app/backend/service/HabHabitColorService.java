package com.letsup.habit.app.backend.service;

import com.letsup.habit.app.backend.dao.entity.HabAppHabitColor;

import java.util.List;

public interface HabHabitColorService {
    /**
     * 获得所有的颜色列表
     * @return
     */
    List<HabAppHabitColor> getAll();
}
