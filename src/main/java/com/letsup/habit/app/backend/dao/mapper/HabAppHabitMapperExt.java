package com.letsup.habit.app.backend.dao.mapper;

import com.letsup.habit.app.backend.dao.entity.HabAppHabit;
import com.letsup.habit.app.backend.vo.MemberHabitListVo;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;

@Resource
public interface HabAppHabitMapperExt extends HabAppHabitMapper {


    /**
     * 通过家庭id获得从今天后的指定日期的习惯列表信息
     * @param familyId
     * @return
     */
    List<HabAppHabit> getFromTodayOnHabitByFamilyId(@Param("familyId") Long familyId, @Param("today") String today);

    /**
     * 通过家庭id获得今天之前指定天的习惯列表信息
     * @param familyId
     * @return
     */
    List<HabAppHabit> getBeforeTodayHabitByFamilyId(@Param("familyId") Long familyId, @Param("yesterday") String yesterday);

    /**
     * 根据条件分页查询习惯记录
     * @param familyId
     * @param status
     * @param start
     * @param pageCount
     * @return
     */
    List<MemberHabitListVo> getPageByCond(@Param("familyId") Long familyId, @Param("status") String status, @Param("today") String today,
                                          @Param("start") Integer start,@Param("pageCount") Integer pageCount);
}