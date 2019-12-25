package com.letsup.habit.app.backend.dao.mapper;

import com.letsup.habit.app.backend.vo.HabitTemplateClassVo;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;

@Resource
public interface HabAppHabitTemplateMapperExt extends HabAppHabitTemplateMapper {
    List<HabitTemplateClassVo> getByTopicId(@Param("topicId") Long topicId);
    List<HabitTemplateClassVo> getByTags(@Param("tags") List<String> tags);
}