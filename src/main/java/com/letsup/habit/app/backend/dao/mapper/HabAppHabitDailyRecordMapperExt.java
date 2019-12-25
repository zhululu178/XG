package com.letsup.habit.app.backend.dao.mapper;

import com.letsup.habit.app.backend.vo.HabitDailyBonusVo;
import com.letsup.habit.app.backend.vo.MemberHabitListVo;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;

@Resource
public interface HabAppHabitDailyRecordMapperExt extends HabAppHabitDailyRecordMapper {
    /**
     * 根据条件分页查询习惯每日信息
     * @param memberId
     * @param start
     * @param pageCount
     * @return
     */
    List<HabitDailyBonusVo> getPageByCond(@Param("memberId") Long memberId,
                                          @Param("start") Integer start, @Param("pageCount") Integer pageCount);
}