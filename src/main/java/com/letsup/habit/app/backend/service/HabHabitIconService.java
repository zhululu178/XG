package com.letsup.habit.app.backend.service;

import com.letsup.habit.app.backend.dao.entity.HabAppHabitIcon;

import java.util.List;

public interface HabHabitIconService {
    /**
     * 获得所有习惯图标
     * @return
     */
    List<HabAppHabitIcon> getAll();
}