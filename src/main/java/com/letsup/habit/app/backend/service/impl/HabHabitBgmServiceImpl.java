package com.letsup.habit.app.backend.service.impl;

import com.letsup.habit.app.backend.dao.entity.HabAppHabit;
import com.letsup.habit.app.backend.dao.entity.HabAppHabitBgm;
import com.letsup.habit.app.backend.dao.mapper.HabAppHabitBgmMapperExt;
import com.letsup.habit.app.backend.service.HabHabitBgmService;
import com.letsup.habit.app.backend.vo.HabitBgmVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HabHabitBgmServiceImpl implements HabHabitBgmService {
    @Autowired
    private HabAppHabitBgmMapperExt habAppHabitBgmMapperExt;

    @Override
    public List<HabitBgmVo> getAll() {
        List<HabitBgmVo> reList = null;
        List<HabAppHabitBgm> list = habAppHabitBgmMapperExt.getAll();
        if(list.size() > 0) {
            reList = new ArrayList<>(list.size());
            for(HabAppHabitBgm bgm : list) {
                HabitBgmVo vo = new HabitBgmVo();
                BeanUtils.copyProperties(bgm, vo);
                reList.add(vo);
            }
        }
        return reList;
    }
}
