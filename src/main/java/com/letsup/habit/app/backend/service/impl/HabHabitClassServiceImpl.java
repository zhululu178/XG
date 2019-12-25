package com.letsup.habit.app.backend.service.impl;

import com.letsup.habit.app.backend.dao.entity.HabAppHabitClass;
import com.letsup.habit.app.backend.dao.mapper.HabAppHabitClassMapperExt;
import com.letsup.habit.app.backend.dao.mapper.HabAppUserMapperExt;
import com.letsup.habit.app.backend.service.HabHabitClassService;
import com.letsup.habit.app.backend.vo.HabitClassVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HabHabitClassServiceImpl implements HabHabitClassService {
    @Autowired
    private HabAppHabitClassMapperExt habAppHabitClassMapperExt;

    @Override
    public List<HabitClassVo> getTopHabitClass() {
        List<HabitClassVo> reList = null;
        List<HabAppHabitClass> list = habAppHabitClassMapperExt.getByParentId(0l);
        if(list.size() > 0) {
            reList = new ArrayList<>(list.size());
            HabitClassVo vo = null;
            for(HabAppHabitClass hc : list) {
                reList.add(this.convertToVo(hc));
            }
        }
        return null;
    }

    private HabitClassVo convertToVo(HabAppHabitClass hc) {
        HabitClassVo vo = new HabitClassVo();
        vo.setId(hc.getId());
        vo.setName(hc.getName());
        vo.setDescription(hc.getDescription());
        vo.setIcon(hc.getIcon());
        return vo;
    }

    @Override
    public List<HabitClassVo> getTopWithChildrenHabitClass() {
        List<HabitClassVo> reList = null;
        List<HabAppHabitClass> list = habAppHabitClassMapperExt.getAll();
        if(list.size() > 0) {
            Map<Long, HabitClassVo> map = new HashMap<>();
            reList = new ArrayList<>();
            HabitClassVo vo = null;
            for(HabAppHabitClass hc : list) {
                vo = this.convertToVo(hc);
                if(hc.getParentId() == 0) {//习惯分类的根
                    reList.add(vo);
                    if(!map.containsKey(hc.getId())) {
                        map.put(hc.getId(), vo);
                    }
                } else {//将分类放入跟分类中
                    HabitClassVo parentVo = map.get(hc.getParentId());
                    if(parentVo.getChildren() == null) {
                        parentVo.setChildren(new ArrayList<>());
                    }
                    parentVo.getChildren().add(vo);
                }
            }
        }
        return reList;
    }
}
