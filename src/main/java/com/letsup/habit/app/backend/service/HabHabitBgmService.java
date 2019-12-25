package com.letsup.habit.app.backend.service;

import com.letsup.habit.app.backend.vo.HabitBgmVo;

import java.util.List;

public interface HabHabitBgmService {
    /**
     * 获得所有的背景音乐列表
     * @return
     */
    List<HabitBgmVo> getAll();
}