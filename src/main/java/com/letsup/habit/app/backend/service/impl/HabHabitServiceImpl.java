package com.letsup.habit.app.backend.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.letsup.habit.app.backend.base.GlobalException;
import com.letsup.habit.app.backend.cond.CreateHabitCond;
import com.letsup.habit.app.backend.cond.CreateHabitFromTemplateCond;
import com.letsup.habit.app.backend.constants.*;
import com.letsup.habit.app.backend.dao.cache.HabAppFamilyMapperCache;
import com.letsup.habit.app.backend.dao.cache.HabAppFamilyMemberMapperCache;
import com.letsup.habit.app.backend.dao.cache.HabAppHabitMapperCache;
import com.letsup.habit.app.backend.dao.entity.*;
import com.letsup.habit.app.backend.dao.mapper.*;
import com.letsup.habit.app.backend.service.HabHabitService;
import com.letsup.habit.app.backend.util.DateTimeUtil;
import com.letsup.habit.app.backend.util.HabitEndDateUtil;
import com.letsup.habit.app.backend.util.rabbitmq.RabbitMqUtil;
import com.letsup.habit.app.backend.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HabHabitServiceImpl implements HabHabitService {
    @Autowired
    private HabAppFamilyMemberMapperCache habAppFamilyMemberMapperExt;
    @Autowired
    private HabAppHabitMapperCache habAppHabitMapperCache;
    @Autowired
    private HabAppHabitDailyRecordMapperExt habAppHabitDailyRecordMapperExt;
    @Autowired
    private HabAppHabitTemplateMapperExt habAppHabitTemplateMapperExt;
    @Autowired
    private HabAppFamilyMapperCache habAppFamilyMapperExt;
    @Autowired
    private HabAppHabitClassMapperExt habAppHabitClassMapperExt;
    @Autowired
    private RabbitMqUtil rabbitMqUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<HabitRemindVo> addByTemplate(Long familyId, Long userId, CreateHabitFromTemplateCond createHabitFromTemplateCond) throws GlobalException {
        List<HabitRemindVo> allReminds = new ArrayList<>();
        if(createHabitFromTemplateCond.getTemplates() != null) {
            checkHabit(familyId, createHabitFromTemplateCond.getTemplates().size());
            HabAppHabitTemplate template = null;
            for(Long templateId : createHabitFromTemplateCond.getTemplates()) {
                template = this.habAppHabitTemplateMapperExt.selectByPrimaryKey(templateId);//获得模板
                if(template != null) {
                    List<HabitRemindVo> reminds = null;
                    if(StringUtils.isNotEmpty(template.getRemind())) {//重置提醒的id字段，已是app端有效
                        reminds = JSONArray.parseArray(template.getRemind(), HabitRemindVo.class);
                        if(reminds != null && reminds.size() > 0) {
                            String idStr = null;
                            for(HabitRemindVo remind : reminds) {
                                idStr = System.currentTimeMillis() + "";
                                remind.setId(Long.parseLong(idStr.substring(1, 11)));
                            }
                            allReminds.addAll(reminds);//将提醒返回客户端
                        }
                    }
                    HabAppHabit habAppHabit = this.getHabitFromTemplate(userId, familyId, template, reminds, createHabitFromTemplateCond.getMemberId(), createHabitFromTemplateCond.getStickDays());
                    if(habAppHabit != null) {
                        this.habAppHabitMapperCache.insertSelective(habAppHabit);
                        this.sendTemplateId(templateId);
                    }
                }
            }
        }
        return allReminds;
    }

    private void sendTemplateId(Long templateId) {
        MessageVo vo = new MessageVo();
        vo.setType(MessageType.TEMPLATE.name());
        vo.setId(templateId);
        this.rabbitMqUtil.directConvertAndSend(vo);
    }

    /**
     * 习惯验证
     * @param familyId
     * @param newNum
     * @throws GlobalException
     */
    private void checkHabit(Long familyId, int newNum) throws GlobalException {
        HabAppFamily family = this.habAppFamilyMapperExt.selectByPrimaryKey(familyId);
        if(family != null && BooleanConstants.YES.equals(family.getIsNew())) {//如果是新家庭需要判断习惯数量
            HabAppHabitExample example = new HabAppHabitExample();
            example.createCriteria().andFamilyIdEqualTo(familyId).andIsDeletedEqualTo("n");
            if(habAppHabitMapperCache.countByExample(example) + newNum > HabConstants.HABIT_NUM) {
                throw new GlobalException(ApiResultConstants.ExceptionMessage.HABIT_NUM_EXCEED);
            }
        }
    }

    /**
     * 根据模板等条件创建习惯
     * @param template
     * @param memberId
     * @param stickDays
     * @return
     */
    private HabAppHabit getHabitFromTemplate(Long userId, Long familyId, HabAppHabitTemplate template, List<HabitRemindVo> reminds, Long memberId, Integer stickDays) throws GlobalException {
        HabAppHabit habAppHabit = null;
        if(template != null) {
            habAppHabit = new HabAppHabit();
            BeanUtils.copyProperties(template, habAppHabit);
            habAppHabit.setTemplateId(template.getId());
            habAppHabit.setFamilyId(familyId);
            habAppHabit.setStickDays(stickDays);
            habAppHabit.setStickedDays(0);
            habAppHabit.setExecutor(memberId);
            habAppHabit.setCreator(userId.toString());
            habAppHabit.setModifier(habAppHabit.getCreator());
            habAppHabit.setStatus(HabitStatusEnum.PROCESSING.name());
            habAppHabit.setVersion(0l);
            if(habAppHabit.getDailyCount() == null) {
                habAppHabit.setDailyCount(1);
            }
            if(habAppHabit.getEachTime() == null) {
                habAppHabit.setEachTime(0);
            }
            if(reminds != null) {//保存提醒
                habAppHabit.setRemind(JSONArray.toJSONString(reminds));
            }
        }
        return habAppHabit;
    }

    @Override
    public void add(Long familyId, Long userId, CreateHabitCond createHabitCond) throws GlobalException {
        checkHabit(familyId, 1);

        HabAppHabit habAppHabit = new HabAppHabit();
        BeanUtils.copyProperties(createHabitCond, habAppHabit);
        habAppHabit.setStickedDays(0);
        habAppHabit.setFamilyId(familyId);
        habAppHabit.setStatus(HabitStatusEnum.PROCESSING.name());
        habAppHabit.setVersion(0l);
        habAppHabit.setCreator(userId.toString());
        habAppHabit.setModifier(habAppHabit.getCreator());
        if(habAppHabit.getDailyCount() == null) {
            habAppHabit.setDailyCount(1);
        }
        if(habAppHabit.getEachTime() == null) {
            habAppHabit.setEachTime(0);
        }
        this.setHabitInfo(createHabitCond, habAppHabit);
        this.habAppHabitMapperCache.insertSelective(habAppHabit);
    }

    @Override
    public void modify(Long familyId, Long userId, CreateHabitCond createHabitCond) throws GlobalException {
        HabAppHabit habAppHabit = this.habAppHabitMapperCache.selectByPrimaryKey(createHabitCond.getId());
        if(habAppHabit != null) {
            if(createHabitCond.getStickDays() < habAppHabit.getStickedDays()) {//修改后的坚持天数，必须小于已坚持的天数
                throw new GlobalException(ApiResultConstants.ExceptionMessage.HABIT_STICK_DAYS_EXCEED);
            }
            BeanUtils.copyProperties(createHabitCond, habAppHabit, new String[]{"id", "type", "executor"});
            habAppHabit.setModifier(userId.toString());
            this.setHabitInfo(createHabitCond, habAppHabit);
            this.habAppHabitMapperCache.updateByPrimaryKeySelective(habAppHabit);
        }
    }

    /**
     * 设置计时
     * @param createHabitCond
     * @param habAppHabit
     */
    private void setHabitRemind(CreateHabitCond createHabitCond, HabAppHabit habAppHabit) {
        if(createHabitCond.getReminds() != null && createHabitCond.getReminds().size() > 0) {//提醒
            habAppHabit.setRemind(JSONObject.toJSONString(createHabitCond.getReminds()));
        }
    }

    /**
     * 设置习惯属性，例如提醒时间，频率天数等
     * @param createHabitCond
     * @param habAppHabit
     */
    private void setHabitInfo(CreateHabitCond createHabitCond, HabAppHabit habAppHabit) {
        habAppHabit.setStartDate(DateTimeUtil.shortStrToDate(createHabitCond.getStartDate()));
        habAppHabit.setEndDate(DateTimeUtil.shortStrToDate(createHabitCond.getEndDate()));
        this.setHabitRemind(createHabitCond, habAppHabit);
        if(createHabitCond.getFreqDays() != null && createHabitCond.getFreqDays().size() > 0) {//频率的天数
            habAppHabit.setFreqDays(StringUtils.join(createHabitCond.getFreqDays().toArray(), ","));
        }
        if(createHabitCond.getCounter() != null && createHabitCond.getCounter().size() > 0) {//计数器
            habAppHabit.setCounter(StringUtils.join(createHabitCond.getCounter().toArray(), ","));
        }
    }

    @Override
    public List<MemberHabitVo> getFromTodayOnHabitByFamilyId(Long familyId, String date) {
        List<HabAppHabit> habits = this.habAppHabitMapperCache.getFromTodayOnHabitByFamilyId(familyId, date);
        return this.getMemberHabitVo(familyId, date, habits);
    }


    @Override
    public List<MemberHabitVo> getBeforeTodayHabitByFamilyId(Long familyId, String date) {
        List<HabAppHabit> habits = this.habAppHabitMapperCache.getBeforeTodayHabitByFamilyId(familyId, date);
        return this.getMemberHabitVo(familyId, date, habits);
    }

    /**
     * 设置成员习惯信息
     * @param habit
     * @param executorDailyGroupBy
     * @return
     */
    private MemberHabitVo setMemberHabitVo(HabAppHabit habit, Map<Long, List<HabAppHabitDailyRecord>> executorDailyGroupBy) {
        MemberHabitVo memberHabitVo = new MemberHabitVo();
        memberHabitVo.setHabits(new ArrayList<>());
        HabAppFamilyMember member = this.habAppFamilyMemberMapperExt.selectByPrimaryKey(habit.getExecutor());
        if(member != null) {
            memberHabitVo.setMemberName(member.getName());
            memberHabitVo.setAvatar(member.getAvatar());
            memberHabitVo.setSex(member.getSex());
            memberHabitVo.setStarAmount(member.getBonus() == null?0:member.getBonus());//星星数量
        }
        if(executorDailyGroupBy != null) {
            List<HabAppHabitDailyRecord> list = executorDailyGroupBy.get(habit.getExecutor());
            if(list != null && list.size() > 0) {
                for(HabAppHabitDailyRecord dailyRecord : list) {
                    if(habit.getDailyCount() > dailyRecord.getActualDailyCount()) {//未完成数量
                        memberHabitVo.setProcessingNum(memberHabitVo.getProcessingNum() + 1);
                    }
                }
            }
        }
        return memberHabitVo;
    }

    /**
     * 根据打卡时间获得家庭的每日打卡信息
     * @param familyId
     * @param clockinDate
     * @return
     */
    private List<HabAppHabitDailyRecord> getDailyRecordsByClockinDate(Long familyId, String clockinDate) {
        HabAppHabitDailyRecordExample example = new HabAppHabitDailyRecordExample();
        example.createCriteria().andFamilyIdEqualTo(familyId).andClockinDateEqualTo(clockinDate).andIsDeletedEqualTo("n");
        return this.habAppHabitDailyRecordMapperExt.selectByExample(example);
    }

    /**
     * 根据家庭，日期和习惯组装列表
     * @param familyId
     * @param date
     * @param habits
     * @return
     */
    private List<MemberHabitVo> getMemberHabitVo(Long familyId, String date, List<HabAppHabit> habits) {
        List<MemberHabitVo> reList = new ArrayList<>();
        if(habits.size() > 0) {
            Map<Long, MemberHabitVo> map = new HashMap<>();
            List<HabAppHabitDailyRecord> dailyRecords = this.getDailyRecordsByClockinDate(familyId, date);
            Map<Long, List<HabAppHabitDailyRecord>> groupBy = null;
            if(dailyRecords.size() > 0) {
                groupBy = dailyRecords.stream().collect(Collectors.groupingBy(HabAppHabitDailyRecord::getExecutor));
            }

            MemberHabitVo memberHabitVo = null;
            HabitSimpleVo habitSimpleVo = null;
            for(HabAppHabit habit : habits) {
                memberHabitVo = map.get(habit.getExecutor());
                if(memberHabitVo == null) {
                    memberHabitVo = this.setMemberHabitVo(habit, groupBy);
                    map.put(habit.getExecutor(), memberHabitVo);
                    reList.add(memberHabitVo);
                }
                habitSimpleVo = new HabitSimpleVo();
                BeanUtils.copyProperties(habit, habitSimpleVo);
                this.setHabitSimpleVo(habitSimpleVo, habit, dailyRecords);//设置是否有日志
                memberHabitVo.getHabits().add(habitSimpleVo);
            }
        }
        return reList;
    }

    /**
     * 是否有日志
     * @param habitSimpleVo
     * @param dailyRecords
     * @return
     */
    private void setHabitSimpleVo(HabitSimpleVo habitSimpleVo, HabAppHabit habit, List<HabAppHabitDailyRecord> dailyRecords) {
        if(StringUtils.isNotEmpty(habit.getCounter())) {
            habitSimpleVo.setCounter(Arrays.asList(habit.getCounter().split(",")));
        }
        if(dailyRecords.size() > 0) {
            for(HabAppHabitDailyRecord dailyRecord : dailyRecords) {
                if(habitSimpleVo.getId().equals(dailyRecord.getHabitId())) {
                    if(StringUtils.isNotEmpty(dailyRecord.getHasLog())) {
                        habitSimpleVo.setHasLog(dailyRecord.getHasLog());
                    } else {
                        habitSimpleVo.setHasLog(BooleanConstants.NO);
                    }
                    if(dailyRecord.getActualDailyCount() != null) {
                        habitSimpleVo.setActualCount(dailyRecord.getActualDailyCount());
                    }
                    if(dailyRecord.getBonus() != null) {
                        habitSimpleVo.setBonus(dailyRecord.getBonus());
                    }
                    if(dailyRecord.getCollectedBonus() != null) {
                        habitSimpleVo.setCollectedBonus(dailyRecord.getCollectedBonus());
                    }
                    break;
                }
            }
        }
    }

    @Override
    public List<MemberHabitListVo> getPageByCond(Long familyId, String status, Integer pageIndex, Integer pageCount) {
        List<MemberHabitListVo> habits = this.habAppHabitMapperCache.getPageByCond(familyId, status, DateTimeUtil.getTodayStr(), pageIndex, pageCount);
        if(habits.size() > 0) {
            for(MemberHabitListVo vo : habits) {
                if(vo.getMemberRole() != null && !FamilyMemberRoleEnum.KID.name().equals(vo.getMemberRole())) {
                    vo.setMemberName(FamilyMemberRoleEnum.valueOf(vo.getMemberRole()).getName());
                }
            }
        }
        return habits;
    }

    @Override
    public void delete(Long id, Long userId) throws GlobalException {
        HabAppHabit habAppHabit = this.habAppHabitMapperCache.selectByPrimaryKey(id);
        if(habAppHabit != null) {
            habAppHabit.setModifier(userId.toString());
            this.habAppHabitMapperCache.deleteByPrimaryKey(habAppHabit);
        }
    }

    @Override
    public void giveUp(Long id, Long userId) throws GlobalException {
        HabAppHabit habAppHabit = this.habAppHabitMapperCache.selectByPrimaryKey(id);
        if(habAppHabit != null) {
            habAppHabit.setStatus(HabitStatusEnum.GIVEUP.name());
            habAppHabit.setModifier(userId.toString());
            this.habAppHabitMapperCache.updateByPrimaryKeySelective(habAppHabit);
        }
    }

    @Override
    public HabitDetailVo getById(Long id) {
        HabitDetailVo habitDetailVo = null;
        HabAppHabit habAppHabit = this.habAppHabitMapperCache.selectByPrimaryKey(id);
        if(habAppHabit != null) {
            habitDetailVo = new HabitDetailVo();
            BeanUtils.copyProperties(habAppHabit, habitDetailVo);
            if(habAppHabit.getStartDate() != null) {//开始日期
                habitDetailVo.setStartDate(DateTimeUtil.dateToStr(habAppHabit.getStartDate(), DateTimeUtil.default_short_pattern));
            }
            if(habAppHabit.getEndDate() != null) {//结束日期
                habitDetailVo.setEndDate(DateTimeUtil.dateToStr(habAppHabit.getEndDate(), DateTimeUtil.default_short_pattern));
            }
            if(StringUtils.isNotEmpty(habAppHabit.getFreqDays())) {//频率天数
                habitDetailVo.setFreqDays(Arrays.asList(habAppHabit.getFreqDays().split(",")));
            }
            if(StringUtils.isNotEmpty(habAppHabit.getCounter())) {//计数器
                habitDetailVo.setCounter(Arrays.asList(habAppHabit.getCounter().split(",")));
            }
            if(StringUtils.isNotEmpty(habAppHabit.getRemind())) {//提醒信息列表
                habitDetailVo.setReminds(JSONArray.parseArray(habAppHabit.getRemind(), HabitRemindVo.class));
            }
            if(habAppHabit.getClass1() != null) {
                habitDetailVo.setClass1Name(this.getHabitClassName(habAppHabit.getClass1()));
            }
            if(habAppHabit.getClass2() != null) {
                habitDetailVo.setClass2Name(this.getHabitClassName(habAppHabit.getClass2()));
            }
        }
        return habitDetailVo;
    }

    /**
     * 根据习惯类型获得名称
     * @param habitClassId
     * @return
     */
    private String getHabitClassName(Long habitClassId) {
        HabAppHabitClass habAppHabitClass = this.habAppHabitClassMapperExt.selectByPrimaryKey(habitClassId);
        if(habAppHabitClass != null) {
            return habAppHabitClass.getName();
        }
        return "";
    }

    @Override
    public void finishExpiredHabit() {
        HabAppHabitExample example = new HabAppHabitExample();
        example.createCriteria().andIsDeletedEqualTo("n").andStatusEqualTo(HabitStatusEnum.PROCESSING.name()).andEndDateLessThan(DateTimeUtil.getToday());
        List<HabAppHabit> habAppHabits = this.habAppHabitMapperCache.selectByExample(example);
        if(habAppHabits.size() > 0) {
            for(HabAppHabit habit : habAppHabits) {
                habit.setStatus(HabitStatusEnum.FINISHED.name());
                this.habAppHabitMapperCache.updateByPrimaryKeySelective(habit);
            }
        }
    }

    @Override
    public void copyFromHabit(Long userId, Long habitId) {
        HabAppHabit originalHabit = this.habAppHabitMapperCache.selectByPrimaryKey(habitId);
        if(originalHabit != null) {
            HabAppHabit newHabit = new HabAppHabit();
            BeanUtils.copyProperties(originalHabit, newHabit, new String[]{"id", "gmtCreate", "gmtModified"});
            newHabit.setStartDate(DateTimeUtil.getToday());
            newHabit.setEndDate(HabitEndDateUtil.generateEndDate(newHabit.getStartDate(), newHabit.getFreqType(), newHabit.getFreqDays(), newHabit.getStickDays()));
            newHabit.setStatus(HabitStatusEnum.PROCESSING.name());
            newHabit.setStickedDays(0);
            newHabit.setCreator(userId.toString());
            newHabit.setModifier(newHabit.getCreator());
            newHabit.setVersion(0l);
            newHabit.setIsDeleted("n");
            this.habAppHabitMapperCache.insertSelective(newHabit);
        }
    }
}