package com.letsup.habit.app.backend.service.impl;

import com.letsup.habit.app.backend.dao.entity.HabAppHabitColor;
import com.letsup.habit.app.backend.dao.mapper.HabAppHabitColorMapperExt;
import com.letsup.habit.app.backend.service.HabHabitColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabHabitColorServiceImpl implements HabHabitColorService {
    @Autowired
    private HabAppHabitColorMapperExt habAppHabitColorMapperExt;

    @Override
    public List<HabAppHabitColor> getAll() {
        return habAppHabitColorMapperExt.getAll();
    }
}
