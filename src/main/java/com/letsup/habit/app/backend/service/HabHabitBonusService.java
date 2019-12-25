package com.letsup.habit.app.backend.service;

import com.letsup.habit.app.backend.base.GlobalException;
import com.letsup.habit.app.backend.vo.HabitDailyBonusVo;
import com.letsup.habit.app.backend.vo.MemberHabitListVo;

import java.util.List;

public interface HabHabitBonusService {
    /**
     * 奖励收集
     * @param userId
     * @param dailyRecordId
     * @return
     */
    int collectBonus(Long userId, Long dailyRecordId) throws GlobalException;

    /**
     * 根据家庭成员id，获得其可进行收集的星星列表
     * @param memberId
     * @param pageIndex
     * @param pageCount
     * @return
     */
    List<HabitDailyBonusVo> getPageByCond(Long memberId, Integer pageIndex, Integer pageCount);
}
