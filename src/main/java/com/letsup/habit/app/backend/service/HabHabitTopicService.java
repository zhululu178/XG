package com.letsup.habit.app.backend.service;

import com.letsup.habit.app.backend.dao.entity.HabAppHabitTopic;
import com.letsup.habit.app.backend.vo.HabitTopicVo;

import java.util.List;

public interface HabHabitTopicService {
    /**
     * 获得所有专题
     * @return
     */
    List<HabitTopicVo> getAll();

    /**
     * 根据id获得专题
     * @return
     */
    HabitTopicVo getById(Long id);
}
