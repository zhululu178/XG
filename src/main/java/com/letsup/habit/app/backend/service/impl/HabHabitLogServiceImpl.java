package com.letsup.habit.app.backend.service.impl;

import com.letsup.habit.app.backend.cond.CreateHabitLogCond;
import com.letsup.habit.app.backend.constants.BooleanConstants;
import com.letsup.habit.app.backend.dao.entity.HabAppHabitDailyRecord;
import com.letsup.habit.app.backend.dao.entity.HabAppHabitDailyRecordExample;
import com.letsup.habit.app.backend.dao.entity.HabAppHabitLog;
import com.letsup.habit.app.backend.dao.entity.HabAppHabitLogExample;
import com.letsup.habit.app.backend.dao.mapper.HabAppHabitDailyRecordMapperExt;
import com.letsup.habit.app.backend.dao.mapper.HabAppHabitLogMapperExt;
import com.letsup.habit.app.backend.service.HabHabitLogService;
import com.letsup.habit.app.backend.util.aop.IsTryAgain;
import com.letsup.habit.app.backend.vo.HabitLogVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Service
public class HabHabitLogServiceImpl implements HabHabitLogService {
    @Autowired
    private HabAppHabitLogMapperExt habAppHabitLogMapperExt;
    @Autowired
    private HabAppHabitDailyRecordMapperExt habAppHabitDailyRecordMapperExt;

    @Override
    @IsTryAgain
    @Transactional(rollbackFor = Exception.class)
    public void add(Long userId, CreateHabitLogCond createHabitLogCond) {
        HabAppHabitLog log = this.addOnlyLog(userId, createHabitLogCond);
        HabAppHabitDailyRecord habitDailyRecord = this.getHabitDailyRecordByClockinDate(createHabitLogCond.getHabitId(), log.getLogDate());
        if(habitDailyRecord != null && !BooleanConstants.YES.equals(habitDailyRecord.getHasLog())) {
            habitDailyRecord.setHasLog(BooleanConstants.YES);
            this.habAppHabitDailyRecordMapperExt.updateByPrimaryKeySelective(habitDailyRecord);
        }
    }

    /**
     * 根据指定日期获得习惯日期打卡记录
     * @param habitId
     * @param clockinDate
     * @return
     */
    private HabAppHabitDailyRecord getHabitDailyRecordByClockinDate(Long habitId, String clockinDate) {
        if(StringUtils.isNotEmpty(clockinDate)) {
            HabAppHabitDailyRecordExample example = new HabAppHabitDailyRecordExample();
            example.createCriteria().andIsDeletedEqualTo("n").andHabitIdEqualTo(habitId).andClockinDateEqualTo(clockinDate);
            List<HabAppHabitDailyRecord> list = this.habAppHabitDailyRecordMapperExt.selectByExample(example);
            if(list.size() > 0) {
                return list.get(0);
            }
        }
        return null;
    }

    @Override
    public HabAppHabitLog addOnlyLog(Long userId, CreateHabitLogCond createHabitLogCond) {
        HabAppHabitLog log = new HabAppHabitLog();
        log.setComments(createHabitLogCond.getComments());
        log.setResourceType(createHabitLogCond.getResourceType());
        log.setHabitId(createHabitLogCond.getHabitId());
        this.setLogResources(log, createHabitLogCond.getResources());
        log.setCreator(userId.toString());
        log.setModifier(log.getCreator());
        if(StringUtils.isNotEmpty(createHabitLogCond.getLogDate())) {
            log.setLogDate(createHabitLogCond.getLogDate().replaceAll("-", ""));
        }
        this.habAppHabitLogMapperExt.insertSelective(log);
        return log;
    }

    @Override
    public void modify(Long userId, CreateHabitLogCond createHabitLogCond) {
        HabAppHabitLog log = this.habAppHabitLogMapperExt.selectByPrimaryKey(createHabitLogCond.getId());
        if(log != null) {
            this.setLogResources(log, createHabitLogCond.getResources());
            log.setComments(createHabitLogCond.getComments());
            log.setResourceType(createHabitLogCond.getResourceType());
            log.setModifier(log.getCreator());
            this.habAppHabitLogMapperExt.updateByPrimaryKeySelective(log);
        }
    }

    private void setLogResources(HabAppHabitLog log, List<String> list) {
        String resource = null;
        Method method = null;
        int i = 1;
        for(;i<=list.size() && i<=9;i++) {
            resource = list.get(i-1);
            if(StringUtils.isNotEmpty(resource)) {
                try {
                    method = log.getClass().getMethod("setResource" + i, String.class);
                    method.invoke(log, resource);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if(log.getId() != null) {//修改
            for(;i<9;i++) {
                try {
                    method = log.getClass().getMethod("setResource" + (i+1), String.class);
                    method.invoke(log, null);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    public static void main(String[] args) {
//        HabAppHabitLog log = new HabAppHabitLog();
//        List<String> list = new ArrayList<>();
//        list.add("1");list.add("2");list.add("3");list.add("4");
//        list.add("5");list.add("6");list.add("7");list.add("8");list.add("9");
//        setLogResources(log, list);
//        System.out.println(log.getResource1() + ":" +log.getResource4() + ":" +log.getResource5());
//        List<String> list1 = getLogResources(log);
//        System.out.println(list1.size());
//    }

    private List<String> getLogResources(HabAppHabitLog log) {
        List<String> list = new ArrayList<>();
        Method method = null;
        for(int i=1;i<=9;i++) {
            try {
                method = log.getClass().getMethod("getResource" + i, null);
                Object reObj = method.invoke(log, null);
                if(reObj != null) {
                    list.add(reObj.toString());
                } else {
                    break;
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    @IsTryAgain
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long userId, Long id) {
        HabAppHabitLog log = this.habAppHabitLogMapperExt.selectByPrimaryKey(id);
        if(log != null) {
            log.setModifier(log.getCreator());
            this.habAppHabitLogMapperExt.deleteByPrimaryKey(log);

            if(this.logCountByHabitDate(log.getHabitId(), log.getLogDate()) == 0) {//如果日志没有了，则设置为无日志
                HabAppHabitDailyRecord habitDailyRecord = this.getHabitDailyRecordByClockinDate(log.getHabitId(), log.getLogDate());
                habitDailyRecord.setHasLog(BooleanConstants.NO);
                this.habAppHabitDailyRecordMapperExt.updateByPrimaryKeySelective(habitDailyRecord);
            }
        }
    }

    /**
     * 指定日期的习惯是否有日志
     * @param habitId
     * @param logDate
     * @return
     */
    private int logCountByHabitDate(Long habitId, String logDate) {
        HabAppHabitLogExample example = new HabAppHabitLogExample();
        example.createCriteria().andHabitIdEqualTo(habitId).andLogDateEqualTo(logDate).andIsDeletedEqualTo("n");
        return this.habAppHabitLogMapperExt.countByExample(example);
    }

    @Override
    public HabitLogVo getById(Long id) {
        HabAppHabitLog log = this.habAppHabitLogMapperExt.selectByPrimaryKey(id);
        return this.convert2Vo(log);
    }

    private HabitLogVo convert2Vo(HabAppHabitLog log) {
        HabitLogVo vo = null;
        if(log != null) {
            vo = new HabitLogVo();
            BeanUtils.copyProperties(log, vo);
            List<String> resources = this.getLogResources(log);
            if(resources.size() > 0) {
                vo.setResources(resources);
            }
        }
        return vo;
    }

    @Override
    public HabitLogVo getLastLogByHabitId(Long habitId, String logDate) {
        HabAppHabitLog log = this.habAppHabitLogMapperExt.getLastLogByHabitId(habitId, logDate);
        return this.convert2Vo(log);
    }
}
