package com.letsup.habit.app.backend.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.letsup.habit.app.backend.base.GlobalException;
import com.letsup.habit.app.backend.base.OptimisticLockException;
import com.letsup.habit.app.backend.cond.ClockinCond;
import com.letsup.habit.app.backend.cond.CreateHabitLogCond;
import com.letsup.habit.app.backend.constants.*;
import com.letsup.habit.app.backend.dao.cache.HabAppFamilyMapperCache;
import com.letsup.habit.app.backend.dao.cache.HabAppFamilyMemberMapperCache;
import com.letsup.habit.app.backend.dao.cache.HabAppHabitMapperCache;
import com.letsup.habit.app.backend.dao.entity.*;
import com.letsup.habit.app.backend.dao.mapper.*;
import com.letsup.habit.app.backend.service.HabHabitClockinService;
import com.letsup.habit.app.backend.service.HabHabitLogService;
import com.letsup.habit.app.backend.util.DateTimeUtil;
import com.letsup.habit.app.backend.util.HabitEndDateUtil;
import com.letsup.habit.app.backend.util.RedisUtil;
import com.letsup.habit.app.backend.util.aop.IsTryAgain;
import com.letsup.habit.app.backend.vo.HabitClockinRecordVo;
import com.letsup.habit.app.backend.vo.HabitClockinVo;
import com.letsup.habit.app.backend.vo.HabitDailyRecordVo;
import com.letsup.habit.app.backend.vo.HabitRemindVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class HabHabitClockinServiceImpl implements HabHabitClockinService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HabAppHabitMapperCache habAppHabitMapperCache;
    @Autowired
    private HabAppFamilyMapperCache habAppFamilyMapperExt;
    @Autowired
    private HabAppFamilyMemberMapperCache habAppFamilyMemberMapperExt;
    @Autowired
    private HabAppHabitDailyRecordMapperExt habAppHabitDailyRecordMapperExt;
    @Autowired
    private HabHabitLogService habHabitLogService;
    @Resource
    private RedisUtil redisUtil;

    @Override
    @IsTryAgain
    @Transactional(rollbackFor = Exception.class)
    public HabitClockinVo clockIn(Long userId, ClockinCond clockinCond) throws GlobalException {
        String today = DateTimeUtil.getTodayStr();//今天，格式yyyy-MM-dd
        logger.info("打卡时间：" + clockinCond.getDate() + "|打卡计数：" + clockinCond.getCount() + "|打卡奖励：" + clockinCond.getBonus());
        long interval = DateTimeUtil.getDaySub(clockinCond.getDate(), today);//只能是今天和昨天的习惯可以打卡
        if( interval > 1 || interval < 0) {
            throw new GlobalException(ApiResultConstants.ExceptionMessage.CLOCKIN_DATE_EXPIRED);
        }
        HabitClockinVo vo = null;
        HabAppHabit habit = this.habAppHabitMapperCache.selectByPrimaryKey(clockinCond.getHabitId());
        if(habit != null) {
            //1. 每天打卡信息
            HabAppHabitDailyRecord habitDailyRecord = this.getHabitDailyRecordByClockinDate(habit.getId(), clockinCond.getDate());
            if(habitDailyRecord == null) {//新增打卡记录
                habitDailyRecord = this.saveHabitDailyRecord(userId, habit, clockinCond.getDate(), BooleanConstants.NO);
            }
            if(habitDailyRecord.getActualDailyCount() == 0 && clockinCond.getCount() < 0) {//打卡为负数的时候，不进行任务操作
                return this.getHabitClockinVo(habit, habitDailyRecord);
            }
            if(BooleanConstants.NO.equals(habitDailyRecord.getIsSticked())) {//是否已转化为有效的坚持天数
                habit.setStickedDays(habit.getStickedDays() + 1);//已打卡天数
                if(habit.getStickedDays() == habit.getStickDays()) {//如果已打卡天数等于应打卡天数，则习惯变成已完成
                    habit.setStatus(HabitStatusEnum.FINISHED.name());
                    this.newFamilyUpgrade(userId, habit.getFamilyId());//家庭升级，只要有一个完成过的习惯，家庭就不再是新家庭
                }
                String beforeDay = DateTimeUtil.addDays(DateTimeUtil.shortStrToDate(clockinCond.getDate()), -1);//前一天
                HabAppHabitDailyRecord beforeHabitDailyRecord = this.getHabitDailyRecordByClockinDate(habit.getId(), beforeDay);
                if(beforeHabitDailyRecord != null && beforeHabitDailyRecord.getActualDailyCount() > 0) {//前一天有成功打卡记录
                    habitDailyRecord.setSerialStartDate(beforeHabitDailyRecord.getSerialStartDate());//使用前一天的连续打卡开始日期
                } else {
                    habitDailyRecord.setSerialStartDate(clockinCond.getDate());
                }
                if(interval == 1) {//说明是补打（只能补打昨天）
                    String afterDay = DateTimeUtil.addDays(DateTimeUtil.shortStrToDate(clockinCond.getDate()), 1);//后一天
                    HabAppHabitDailyRecord afterHabitDailyRecord = this.getHabitDailyRecordByClockinDate(habit.getId(), afterDay);
                    if(afterHabitDailyRecord != null) {//后一天不要使用补打卡的连续打卡开始日期
                        afterHabitDailyRecord.setSerialStartDate(habitDailyRecord.getSerialStartDate());
                        afterHabitDailyRecord.setModifier(userId.toString());
                        if(this.habAppHabitDailyRecordMapperExt.updateByPrimaryKeySelective(afterHabitDailyRecord) <= 0) {
                            throw new OptimisticLockException(ApiResultConstants.ExceptionMessage.OPTIMISTIC_LOCK_ERROR);
                        }
                    }
                }
                //如果是模板类型，没有开始和结束日期，需要自动设置开始和结束日期，并且取消打卡不影响已经起作用的开始和结束日期
                if(habit.getTemplateId() != null && habit.getStartDate() == null && habit.getEndDate() == null) {
                    habit.setStartDate(DateTimeUtil.getToday());
                    habit.setEndDate(HabitEndDateUtil.generateEndDate(habit.getStartDate(), habit.getFreqType(), habit.getFreqDays(), habit.getStickDays()));
                }
                habit.setModifier(userId.toString());
                if(this.habAppHabitMapperCache.updateByPrimaryKeySelective(habit) <= 0) {
                    throw new OptimisticLockException(ApiResultConstants.ExceptionMessage.OPTIMISTIC_LOCK_ERROR);
                }
                habitDailyRecord.setIsSticked(BooleanConstants.YES);
            } else if(BooleanConstants.YES.equals(habitDailyRecord.getIsSticked()) && (habitDailyRecord.getActualDailyCount() + clockinCond.getCount() == 0)) {//打卡数量恢复为0，需要回退打卡信息
                this.undoClickin(habit, habitDailyRecord, userId, clockinCond.getDate());
            }

            //3. 汇总更新每日打卡信息
            if(habitDailyRecord.getBonus() + clockinCond.getBonus() + habitDailyRecord.getCollectedBonus()> HabConstants.STAR_NUM) {
                throw new GlobalException(ApiResultConstants.ExceptionMessage.CLOCKIN_BONUS_EXCEED);
            }
            if(BooleanConstants.YES.equals(clockinCond.getNotDoToday())) {//如果今日不做，就不进行打卡记录的保存
                habitDailyRecord.setNotDoToday(BooleanConstants.YES);//今日不做
            }
            habitDailyRecord.setActualDailyCount(habitDailyRecord.getActualDailyCount() + clockinCond.getCount());
            if(habitDailyRecord.getActualDailyCount() < 0) {//为负数的时候，变成0
                habitDailyRecord.setActualDailyCount(0);
            }
            Integer originalBonus = habitDailyRecord.getBonus();
            habitDailyRecord.setBonus(originalBonus + clockinCond.getBonus());
            if(habitDailyRecord.getBonus() < 0) {//为负数的时候，变成0，需要减掉成员的星星数
                clockinCond.setBonus(0 - originalBonus);
                habitDailyRecord.setBonus(0);
            }
            habitDailyRecord.setModifier(userId.toString());
            if(this.habAppHabitDailyRecordMapperExt.updateByPrimaryKeySelective(habitDailyRecord) <= 0) {
                throw new OptimisticLockException(ApiResultConstants.ExceptionMessage.OPTIMISTIC_LOCK_ERROR);
            }
            //4. 更新家庭成员的星星总数
            this.saveMemberBonus(userId, habit.getExecutor(), clockinCond.getBonus());
            //5. 每次打卡记录，放入缓存
            if(!BooleanConstants.YES.equals(clockinCond.getNotDoToday())) {//如果今日不做，就不进行打卡记录的保存
                HabitClockinRecordVo habitClockinRecordVo = new HabitClockinRecordVo();
                habitClockinRecordVo.setDailyRecordId(habitDailyRecord.getId());
                habitClockinRecordVo.setBonus(clockinCond.getBonus());
                habitClockinRecordVo.setCount(clockinCond.getCount());
                this.redisUtil.lSet(RedisConstant.CLOCKIN_RECORD + habitDailyRecord.getId(), habitClockinRecordVo, RedisTimeEnum.ExpireEnum.CLOCKIN_RECORD.getTime());
            }
            vo = this.getHabitClockinVo(habit, habitDailyRecord);
        }
        return vo;
    }

    /**
     * 返回打卡信息
     * @param habit
     * @param habitDailyRecord
     * @return
     */
    private HabitClockinVo getHabitClockinVo(HabAppHabit habit, HabAppHabitDailyRecord habitDailyRecord) {
        //6. 返回信息
        HabitClockinVo vo = new HabitClockinVo();
        vo.setHabitId(habit.getId());
        vo.setStatus(habit.getStatus());
        vo.setActualCount(habitDailyRecord.getActualDailyCount());
        vo.setBonus(habitDailyRecord.getBonus());
        if(StringUtils.isNotEmpty(habit.getRemind())) {
            vo.setReminds(JSONArray.parseArray(habit.getRemind(), HabitRemindVo.class));
        }
        return vo;
    }

    @Override
    @IsTryAgain
    @Transactional(rollbackFor = Exception.class)
    public void notDoToday(Long userId, CreateHabitLogCond createHabitLogCond) throws GlobalException {
        HabAppHabit habit = this.habAppHabitMapperCache.selectByPrimaryKey(createHabitLogCond.getHabitId());
        if(habit != null) {
            //1. 新增习惯日志
            if(StringUtils.isNotEmpty(createHabitLogCond.getResourceType()) ||
                    (createHabitLogCond.getResources() != null && createHabitLogCond.getResources().size() > 0)) {//必须输入日志或者资源内容才会新增日志
                this.habHabitLogService.addOnlyLog(userId, createHabitLogCond);
            }

            //2. 每天打卡信息
            String today = DateTimeUtil.getTodayStr();
            HabAppHabitDailyRecord habitDailyRecord = this.getHabitDailyRecordByClockinDate(habit.getId(), today);
            if(habitDailyRecord == null) {//新增打卡记录
                habitDailyRecord = this.saveHabitDailyRecord(userId, habit, today, BooleanConstants.YES);
            }
            if(BooleanConstants.NO.equals(habitDailyRecord.getIsSticked())) {//是否已转化为有效的坚持天数
                habit.setStickedDays(habit.getStickedDays() + 1);//已打卡天数
                if(habit.getStickedDays() == habit.getStickDays()) {//如果已打卡天数等于应打卡天数，则习惯变成已完成
                    habit.setStatus(HabitStatusEnum.FINISHED.name());
                    this.newFamilyUpgrade(userId, habit.getFamilyId());//家庭升级，只要有一个完成过的习惯，家庭就不再是新家庭
                }
                habit.setModifier(userId.toString());
                if(this.habAppHabitMapperCache.updateByPrimaryKeySelective(habit) <= 0) {
                    throw new OptimisticLockException(ApiResultConstants.ExceptionMessage.OPTIMISTIC_LOCK_ERROR);
                }
                habitDailyRecord.setIsSticked(BooleanConstants.YES);
            }
            int starNum = 0 - habitDailyRecord.getBonus();//星星数量要减成0
            habitDailyRecord.setBonus(0);
            habitDailyRecord.setActualDailyCount(0);
            habitDailyRecord.setHasLog(BooleanConstants.YES);
            habitDailyRecord.setModifier(userId.toString());
            if(this.habAppHabitDailyRecordMapperExt.updateByPrimaryKeySelective(habitDailyRecord) <= 0) {
                throw new OptimisticLockException(ApiResultConstants.ExceptionMessage.OPTIMISTIC_LOCK_ERROR);
            }
            //3. 更新家庭成员的星星总数
            this.saveMemberBonus(userId, habit.getExecutor(), starNum);
        }
    }

    /**
     * 保存家庭成员星星奖励数量
     * @param memberId
     * @param bonus
     */
    private void saveMemberBonus(Long userId, Long memberId, int bonus) throws OptimisticLockException {
        if(bonus != 0) {//如果星星奖励有变化，就更新用户的星星总数
            HabAppFamilyMember member = this.habAppFamilyMemberMapperExt.selectByPrimaryKey(memberId);
            if(member != null) {
                member.setBonus(member.getBonus() == null?bonus:member.getBonus() + bonus);
                member.setModifier(userId.toString());
                if(this.habAppFamilyMemberMapperExt.updateByPrimaryKeySelective(member) <= 0) {
                    throw new OptimisticLockException(ApiResultConstants.ExceptionMessage.OPTIMISTIC_LOCK_ERROR);
                }
            }
        }
    }

    /**
     * 新家庭升级为非新家庭
     * @param userId
     * @param familyId
     */
    private void newFamilyUpgrade(Long userId, Long familyId) {
        HabAppFamily family = this.habAppFamilyMapperExt.selectByPrimaryKey(familyId);
        if(family != null && BooleanConstants.YES.equals(family.getIsNew())) {
            family.setIsNew(BooleanConstants.NO);
            family.setModifier(userId.toString());
            this.habAppFamilyMapperExt.updateByPrimaryKeySelective(family);
        }
    }

    /**
     * 保存习惯每日记录信息
     * @param userId
     * @param habit
     * @param clockinDate
     * @return
     */
    private HabAppHabitDailyRecord saveHabitDailyRecord(Long userId, HabAppHabit habit, String clockinDate, String notDoToday) {
        HabAppHabitDailyRecord habitDailyRecord = new HabAppHabitDailyRecord();
        habitDailyRecord.setHabitId(habit.getId());
        habitDailyRecord.setFamilyId(habit.getFamilyId());
        habitDailyRecord.setExecutor(habit.getExecutor());
        habitDailyRecord.setActualDailyCount(0);
        habitDailyRecord.setBonus(0);
        habitDailyRecord.setCollectedBonus(0);
        habitDailyRecord.setClockinDate(clockinDate);
        habitDailyRecord.setIsSticked(BooleanConstants.NO);
        habitDailyRecord.setHasLog(BooleanConstants.NO);
        habitDailyRecord.setNotDoToday(notDoToday);
        habitDailyRecord.setCreator(userId.toString());
        habitDailyRecord.setModifier(habitDailyRecord.getCreator());
        habitDailyRecord.setVersion(0l);
        this.habAppHabitDailyRecordMapperExt.insertSelective(habitDailyRecord);
        //创建打卡记录的redis的list

        return habitDailyRecord;
    }

    /**
     * 根据指定日期获得习惯日期打卡记录
     * @param habitId
     * @param clockinDate
     * @return
     */
    private HabAppHabitDailyRecord getHabitDailyRecordByClockinDate(Long habitId, String clockinDate) {
        HabAppHabitDailyRecordExample example = new HabAppHabitDailyRecordExample();
        example.createCriteria().andIsDeletedEqualTo("n").andHabitIdEqualTo(habitId).andClockinDateEqualTo(clockinDate);
        List<HabAppHabitDailyRecord> list = this.habAppHabitDailyRecordMapperExt.selectByExample(example);
        if(list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    //打开回退，更新习惯
    private void undoClickin(HabAppHabit habit, HabAppHabitDailyRecord habitDailyRecord, Long userId, String clockinDate) throws GlobalException {
        habit.setStickedDays(habit.getStickedDays() == 1? 0: habit.getStickedDays() - 1);
        if(habit.getStickedDays() != habit.getStickDays()) {//如果已打卡天数与应打卡天数不相等，则习惯变成已进行中
            habit.setStatus(HabitStatusEnum.PROCESSING.name());
        }
        habit.setModifier(userId.toString());
        if(this.habAppHabitMapperCache.updateByPrimaryKeySelective(habit) <= 0) {
            throw new OptimisticLockException(ApiResultConstants.ExceptionMessage.OPTIMISTIC_LOCK_ERROR);
        }
        habitDailyRecord.setSerialStartDate(clockinDate);
        habitDailyRecord.setIsSticked(BooleanConstants.NO);
    }

    @Override
    @IsTryAgain
    @Transactional(rollbackFor = Exception.class)
    public HabitDailyRecordVo cancelClockIn(Long userId, Long habitId) throws GlobalException {
        HabAppHabit habit = this.habAppHabitMapperCache.selectByPrimaryKey(habitId);
        HabAppHabitDailyRecord habitDailyRecord = null;
        if(habit != null) {
            String clockinDate = DateTimeUtil.getTodayStr();
            habitDailyRecord = this.getHabitDailyRecordByClockinDate(habit.getId(), clockinDate);
            if(habitDailyRecord != null) {
                Object lastClockinObj = this.redisUtil.rifhtPop(RedisConstant.CLOCKIN_RECORD + habitDailyRecord.getId());
                if(lastClockinObj != null) {
                    HabitClockinRecordVo clockinRecordVo = (HabitClockinRecordVo)lastClockinObj;
                    if(habitDailyRecord.getActualDailyCount() == 0 && clockinRecordVo.getCount() >= 0) {//不进行打卡撤销，因为打卡计数会变成负数

                    } else {
                        //1. 根据打卡记录进行撤销操作
                        habitDailyRecord.setActualDailyCount(habitDailyRecord.getActualDailyCount() - clockinRecordVo.getCount());
                        if(habitDailyRecord.getActualDailyCount() < 0) {//设置计数量
                            habitDailyRecord.setActualDailyCount(0);
                        }
                        Integer originalBonus = habitDailyRecord.getBonus();
                        habitDailyRecord.setBonus(originalBonus - clockinRecordVo.getBonus());
                        if(habitDailyRecord.getBonus() < 0) {//为负数的时候，变成0，需要减掉成员的星星数
                            clockinRecordVo.setBonus(originalBonus);
                            habitDailyRecord.setBonus(0);
                        }
                        if(habitDailyRecord.getBonus() + habitDailyRecord.getCollectedBonus() > HabConstants.STAR_NUM) {//是否超过星星数量
                            throw new GlobalException(ApiResultConstants.ExceptionMessage.CLOCKIN_BONUS_EXCEED);
                        }
                        //2. 是否需要更新习惯状态和坚持天数
                        if(BooleanConstants.YES.equals(habitDailyRecord.getIsSticked()) && habitDailyRecord.getActualDailyCount() == 0) {//是否已转化为有效的坚持天数
                            this.undoClickin(habit, habitDailyRecord, userId, clockinDate);
                        }
                        //3. 更新习惯每天记录
                        habitDailyRecord.setModifier(userId.toString());
                        if(this.habAppHabitDailyRecordMapperExt.updateByPrimaryKeySelective(habitDailyRecord) <= 0) {
                            throw new OptimisticLockException(ApiResultConstants.ExceptionMessage.OPTIMISTIC_LOCK_ERROR);
                        }
                        //4. 更新家庭成员的星星总数
                        this.saveMemberBonus(userId, habit.getExecutor(), 0 - clockinRecordVo.getBonus());
                    }
                }
            }
        } else {
            throw new GlobalException(ApiResultConstants.ExceptionMessage.HABIT_NOT_EXISTS);
        }
        return this.convert2DailyRecordVo(habit, habitDailyRecord);
    }

    /**
     * 习惯每日记录转换
     * @param habit
     * @param habitDailyRecord
     * @return
     */
    private HabitDailyRecordVo convert2DailyRecordVo(HabAppHabit habit, HabAppHabitDailyRecord habitDailyRecord) {
        HabitDailyRecordVo vo = new HabitDailyRecordVo();
        vo.setHabitId(habit.getId());
        vo.setHabitTitle(habit.getTitle());
        vo.setDailyCount(habit.getDailyCount());
        vo.setEachTime(habit.getEachTime());
        vo.setType(habit.getType());
        vo.setTimer(habit.getTimer());
        if(StringUtils.isNotEmpty(habit.getCounter())) {
            vo.setCounter(Arrays.asList(habit.getCounter().split(",")));
        }
        if(habitDailyRecord != null) {
            vo.setBonus(habitDailyRecord.getBonus());
            vo.setCollectedBonus(habitDailyRecord.getCollectedBonus());
            vo.setCount(habitDailyRecord.getActualDailyCount());
            vo.setNotDoToday(habitDailyRecord.getNotDoToday());
        }
        return vo;
    }

    @Override
    public HabitDailyRecordVo getByHabitId(Long habitId, String clockinDate) throws GlobalException {
        HabAppHabit habit = this.habAppHabitMapperCache.selectByPrimaryKey(habitId);
        HabAppHabitDailyRecord habitDailyRecord = null;
        if(habit != null) {
            habitDailyRecord = this.getHabitDailyRecordByClockinDate(habit.getId(), clockinDate);
        } else {
            throw new GlobalException(ApiResultConstants.ExceptionMessage.HABIT_NOT_EXISTS);
        }
        return this.convert2DailyRecordVo(habit, habitDailyRecord);
    }
}