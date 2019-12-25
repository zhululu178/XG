package com.letsup.habit.app.backend.service.impl;

import com.letsup.habit.app.backend.base.GlobalException;
import com.letsup.habit.app.backend.base.OptimisticLockException;
import com.letsup.habit.app.backend.constants.ApiResultConstants;
import com.letsup.habit.app.backend.constants.HabConstants;
import com.letsup.habit.app.backend.dao.cache.HabAppFamilyMemberMapperCache;
import com.letsup.habit.app.backend.dao.entity.HabAppFamilyMember;
import com.letsup.habit.app.backend.dao.entity.HabAppHabitDailyRecord;
import com.letsup.habit.app.backend.dao.mapper.HabAppHabitDailyRecordMapperExt;
import com.letsup.habit.app.backend.service.HabHabitBonusService;
import com.letsup.habit.app.backend.vo.HabitDailyBonusVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.letsup.habit.app.backend.constants.ApiResultConstants.ExceptionMessage.*;

@Service
public class HabHabitBonusServiceImpl implements HabHabitBonusService {
    @Autowired
    private HabAppFamilyMemberMapperCache habAppFamilyMemberMapperExt;
    @Autowired
    private HabAppHabitDailyRecordMapperExt habAppHabitDailyRecordMapperExt;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int collectBonus(Long userId, Long dailyRecordId) throws GlobalException {
        HabAppHabitDailyRecord dailyRecord = this.habAppHabitDailyRecordMapperExt.selectByPrimaryKey(dailyRecordId);
        if(dailyRecord != null && dailyRecord.getBonus() != null && dailyRecord.getBonus() != 0) {//是有可以收集的星星
            if(dailyRecord.getBonus() + dailyRecord.getCollectedBonus() > HabConstants.STAR_NUM) {
                throw new GlobalException(COLLECTED_BONUS_NUM_EXCEED);
            }
            int bonus = dailyRecord.getBonus();//待收集的星星数量
            dailyRecord.setModifier(userId.toString());
            dailyRecord.setCollectedBonus(dailyRecord.getBonus() + dailyRecord.getCollectedBonus());
            dailyRecord.setBonus(0);
            if(this.habAppHabitDailyRecordMapperExt.updateByPrimaryKeySelective(dailyRecord) <= 0) {//更新失败
                throw new OptimisticLockException(ApiResultConstants.ExceptionMessage.OPTIMISTIC_LOCK_ERROR);
            }
            return this.saveMemberBonus(userId, dailyRecord.getExecutor(), bonus);//增加到用户的星星账号中
        }
        throw new GlobalException(COLLECTED_BONUS_ERROR);
    }

    /**
     * 保存家庭成员星星奖励数量
     * @param memberId
     * @param bonus
     */
    private int saveMemberBonus(Long userId, Long memberId, int bonus) throws OptimisticLockException {
        HabAppFamilyMember member = this.habAppFamilyMemberMapperExt.selectByPrimaryKey(memberId);
        if(bonus != 0) {//如果星星奖励有变化，就更新用户的星星总数
            if(member != null) {
                member.setBonus(member.getBonus() - bonus);//从待收集的星星数量中减掉
                member.setCollectBonus(member.getCollectBonus() + bonus);//已收集星星数量中加上
//                if(member.getBonus() < 0) {//如果有负数就等于0
//                    member.setBonus(0);
//                }
                member.setModifier(userId.toString());
                if(this.habAppFamilyMemberMapperExt.updateByPrimaryKeySelective(member) <= 0) {
                    throw new OptimisticLockException(ApiResultConstants.ExceptionMessage.OPTIMISTIC_LOCK_ERROR);
                }
            }
        }
        return member.getCollectBonus();
    }

    @Override
    public List<HabitDailyBonusVo> getPageByCond(Long memberId, Integer pageIndex, Integer pageCount) {
        return this.habAppHabitDailyRecordMapperExt.getPageByCond(memberId, pageIndex, pageCount);
    }
}
