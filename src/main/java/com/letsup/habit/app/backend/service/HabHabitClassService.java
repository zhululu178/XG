package com.letsup.habit.app.backend.service;

import com.letsup.habit.app.backend.dao.entity.HabAppHabitClass;
import com.letsup.habit.app.backend.vo.HabitClassVo;

import java.util.List;

public interface HabHabitClassService {
    /**
     * 获得所有最高层次的习惯分类
     * @return
     */
    List<HabitClassVo> getTopHabitClass();
    /**
     * 获得所有最高层次的习惯分类，包括子项
     * @return
     */
    List<HabitClassVo> getTopWithChildrenHabitClass();
}
