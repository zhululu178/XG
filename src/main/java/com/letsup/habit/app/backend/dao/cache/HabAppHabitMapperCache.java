package com.letsup.habit.app.backend.dao.cache;

import com.letsup.habit.app.backend.constants.RedisConstant;
import com.letsup.habit.app.backend.dao.entity.HabAppHabit;
import com.letsup.habit.app.backend.dao.entity.HabAppHabitExample;
import com.letsup.habit.app.backend.dao.mapper.HabAppHabitMapperExt;
import com.letsup.habit.app.backend.util.DateTimeUtil;
import com.letsup.habit.app.backend.vo.MemberHabitListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HabAppHabitMapperCache extends BaseMapperCache<HabAppHabit, HabAppHabitExample> implements HabAppHabitMapperExt {
    @Autowired
    private HabAppHabitMapperExt habAppHabitMapperExt;

    @Override
    protected long getCacheTime() {
        //60*60*24=86400 1天
        return 86400;
    }

    @Override
    protected Object getMapper() {
        return this.habAppHabitMapperExt;
    }

    @Override
    protected void clearRelatedCache(HabAppHabit record) {
        if(record != null) {//清除缓存
            this.redisUtil.del(RedisConstant.FAMILY_HABIT_YESTERDAY_RECORD + DateTimeUtil.getTomorrowStr() + record.getFamilyId());//昨天
            this.redisUtil.del(RedisConstant.FAMILY_HABIT_TODAY_RECORD + DateTimeUtil.getTodayStr() + record.getFamilyId());//今天
            this.redisUtil.del(RedisConstant.FAMILY_HABIT_TOMORROW_RECORD + DateTimeUtil.getTomorrowStr() + record.getFamilyId());//明天
        }
    }

    @Override
    public List<HabAppHabit> getFromTodayOnHabitByFamilyId(Long familyId, String today) {
        String key = RedisConstant.FAMILY_HABIT_TODAY_RECORD + today + familyId;//今天
        if(!DateTimeUtil.getTodayStr().equals(today)) {//明天
            key = RedisConstant.FAMILY_HABIT_TOMORROW_RECORD + today + familyId;
        }
        Object cacheObj = this.redisUtil.get(key);
        List<HabAppHabit> list = null;
        if(cacheObj == null) {
            list = this.habAppHabitMapperExt.getFromTodayOnHabitByFamilyId(familyId, today);
            this.redisUtil.set(key, list, this.getCacheTime());
        } else {
            list = (List<HabAppHabit>) cacheObj;
        }
        return list;
    }

    @Override
    public List<HabAppHabit> getBeforeTodayHabitByFamilyId(Long familyId, String yesterday) {
        String key = RedisConstant.FAMILY_HABIT_YESTERDAY_RECORD + yesterday + familyId;//昨天
        if(!DateTimeUtil.getYesterdayStr().equals(yesterday)) {//昨天之前的历史天
            key = RedisConstant.FAMILY_HABIT_BEFORE_YESTERDAY_RECORD + yesterday + familyId;
        }
        Object cacheObj = this.redisUtil.get(key);
        List<HabAppHabit> list = null;
        if(cacheObj == null) {
            list = this.habAppHabitMapperExt.getBeforeTodayHabitByFamilyId(familyId, yesterday);
            this.redisUtil.set(key, list, this.getCacheTime());
        } else {
            list = (List<HabAppHabit>) cacheObj;
        }
        return list;
    }

    @Override
    public List<MemberHabitListVo> getPageByCond(Long familyId, String status, String today, Integer start, Integer pageCount) {
        return this.habAppHabitMapperExt.getPageByCond(familyId, status, today, start, pageCount);
    }
}
