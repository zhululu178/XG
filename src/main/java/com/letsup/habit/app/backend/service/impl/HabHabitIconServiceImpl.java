package com.letsup.habit.app.backend.service.impl;

import com.letsup.habit.app.backend.dao.entity.HabAppHabitIcon;
import com.letsup.habit.app.backend.dao.mapper.HabAppHabitIconMapperExt;
import com.letsup.habit.app.backend.service.HabHabitIconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabHabitIconServiceImpl implements HabHabitIconService {
    @Autowired
    private HabAppHabitIconMapperExt habAppHabitIconMapperExt;

    @Override
    public List<HabAppHabitIcon> getAll() {
        return habAppHabitIconMapperExt.getAll();
    }
}
