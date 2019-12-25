package com.letsup.habit.app.backend.service.impl;

import com.letsup.habit.app.backend.dao.entity.*;
import com.letsup.habit.app.backend.dao.mapper.HabAppHabitClassMapperExt;
import com.letsup.habit.app.backend.dao.mapper.HabAppHabitTemplateMapperExt;
import com.letsup.habit.app.backend.dao.mapper.HabAppHabitTopicMapperExt;
import com.letsup.habit.app.backend.service.HabHabitTopicService;
import com.letsup.habit.app.backend.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HabHabitTopicServiceImpl implements HabHabitTopicService {
    @Autowired
    private HabAppHabitTopicMapperExt habAppHabitTopicMapperExt;
    @Autowired
    private HabAppHabitTemplateMapperExt habAppHabitTemplateMapperExt;
    @Autowired
    private HabAppHabitClassMapperExt habAppHabitClassMapperExt;

    @Override
    public List<HabitTopicVo> getAll() {
        List<HabitTopicVo> reList = null;
        HabAppHabitTopicExample example = new HabAppHabitTopicExample();
        example.createCriteria().andIsDeletedEqualTo("n");
        List<HabAppHabitTopic> list = this.habAppHabitTopicMapperExt.selectByExample(example);
        if(list.size() > 0) {
            reList = new ArrayList<>(list.size());
            for(HabAppHabitTopic topic : list) {
                HabitTopicVo vo = new HabitTopicVo();
                BeanUtils.copyProperties(topic, vo);
                reList.add(vo);
            }
        }
        return reList;
    }

    @Override
    public HabitTopicVo getById(Long id) {
        HabitTopicVo habitTopicVo = null;
        HabAppHabitTopic topic = this.habAppHabitTopicMapperExt.selectByPrimaryKey(id);
        if(topic != null) {
            habitTopicVo = new HabitTopicVo();
            BeanUtils.copyProperties(topic, habitTopicVo);
            habitTopicVo.setTemplateClasses(this.habAppHabitTemplateMapperExt.getByTopicId(id));
        }
        return habitTopicVo;
    }
}