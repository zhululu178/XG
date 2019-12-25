package com.letsup.habit.app.backend.util.aop;

import com.letsup.habit.app.backend.dao.entity.HabAppHabit;
import com.letsup.habit.app.backend.dao.mapper.HabAppFamilyMapper;
import com.letsup.habit.app.backend.dao.mapper.HabAppFamilyMemberMapper;
import com.letsup.habit.app.backend.dao.mapper.HabAppHabitMapper;
import com.letsup.habit.app.backend.util.RedisUtil;
import com.letsup.habit.app.backend.util.ReflectionUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * mybaits基础方法缓存:
 * selectByPrimaryKey,deleteByPrimaryKey,updateByPrimaryKeySelective,insertSelective
 * 已经不用
 */
//@Aspect
//@Component
public class MybatisMapperExecutor implements Ordered {
    @Resource
    protected RedisUtil redisUtil;

    private static Map<String, Long> CACHE_CLASS = new HashMap<>(3);
    static {
        //1天
        CACHE_CLASS.put(HabAppHabitMapper.class.getName(), 60*60*24l);
        CACHE_CLASS.put(HabAppFamilyMemberMapper.class.getName(), 60*60*24l);
        //30天
        CACHE_CLASS.put(HabAppFamilyMapper.class.getName(), 60*60*24*30l);
    }

    @Override
    public int getOrder() {
        return 100;
    }

    /**
     * 根据id获取内容
     */
    @Pointcut("execution(* com.letsup.habit.app.backend.dao.mapper..*.selectByPrimaryKey(..))")
    public void mapperSelect() {
    }

    /**
     * 删除对象
     */
    @Pointcut("execution(* com.letsup.habit.app.backend.dao.mapper..*.deleteByPrimaryKey(..))")
    public void mapperDelete() {
    }

    /**
     * 修改对象
     */
    @Pointcut("execution(* com.letsup.habit.app.backend.dao.mapper..*.updateByPrimaryKeySelective(..))")
    public void mapperUpdate() {
    }

    /**
     * 新增对象
     */
    @Pointcut("execution(* com.letsup.habit.app.backend.dao.mapper..*.insertSelective(..))")
    public void mapperInsert() {
    }

    @Around("mapperSelect()")
    public Object doMapperSelect(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        if(signature != null && CACHE_CLASS.containsKey(signature.getDeclaringTypeName())) {
            Object[] args = pjp.getArgs();
            if(args != null && args.length == 1) {
                Long id = (Long) args[0];
                Object obj = redisUtil.get(signature.getDeclaringType().getName() + id);
                if(obj == null) {
                    obj = pjp.proceed();
                    if(obj != null) {
                        redisUtil.set(pjp.getSignature().getDeclaringType().getName() + id, obj, CACHE_CLASS.get(signature.getDeclaringTypeName()));
                    }
                }
                return obj;
            }
        }
        return pjp.proceed();
    }

    @Around("mapperDelete()")
    public Object doMapperDelete(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        if(signature != null && CACHE_CLASS.containsKey(signature.getDeclaringTypeName())) {
            Object[] args = pjp.getArgs();
            if(args != null && args.length == 1) {
                Object reObj = pjp.proceed();
                if(reObj instanceof Integer) {
                    int rows = (Integer)reObj;
                    if(rows > 0) {
                        Long id = ReflectionUtil.getFieldValueById("id", args[0]);
                        redisUtil.del(signature.getDeclaringType().getName() + id);
                        return reObj;
                    }
                }
            }
        }
        return pjp.proceed();
    }

    @Around("mapperUpdate() || mapperInsert()")
    public Object doMapperUpdate(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        if(signature != null && CACHE_CLASS.containsKey(signature.getDeclaringTypeName())) {
            Object[] args = pjp.getArgs();
            if(args != null && args.length == 1) {
                Object reObj = pjp.proceed();
                if(reObj instanceof Integer) {
                    int rows = (Integer)reObj;
                    if(rows > 0) {
                        Long id = ReflectionUtil.getFieldValueById("id", args[0]);
                        redisUtil.set(pjp.getSignature().getDeclaringType().getName() + id, args[0], CACHE_CLASS.get(signature.getDeclaringTypeName()));
                        return reObj;
                    }
                }
            }
        }
        return pjp.proceed();
    }
}