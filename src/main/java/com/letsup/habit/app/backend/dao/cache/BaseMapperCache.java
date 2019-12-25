package com.letsup.habit.app.backend.dao.cache;

import com.letsup.habit.app.backend.util.RedisUtil;
import com.letsup.habit.app.backend.util.ReflectionUtil;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class BaseMapperCache<V, K> {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected RedisUtil redisUtil;

    protected Class entityClass;

    private Map<String, Method> methodMap = new HashMap<>(5);

    public BaseMapperCache() {
        if(this.entityClass == null){
            this.entityClass = (Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
        }
    }

    /**
     * mybatis mapper类
     * @return
     */
    protected abstract Object getMapper();

    /**
     * 缓存时间，单位是秒
     * @return
     */
    protected abstract long getCacheTime();

    /**
     * 清楚相关的缓存
     * @return
     */
    protected void clearRelatedCache(V record) {
    }

    private Method getMethod(String methodName, Object parameter) {
        Method method = this.methodMap.get(methodName);
        if(method == null) {
            method = ReflectionUtil.getMethodByName(this.getMapper().getClass(), methodName, parameter.getClass());
            Objects.nonNull(method);
            methodMap.put(methodName, method);
        }
        return method;
    }

    /**
     * 删除方法
     * @param record
     * @return
     */
    public int deleteByPrimaryKey(V record) {
        Method method = this.getMethod("deleteByPrimaryKey", record);
        int rows = 0;
        try {
            Object reObj = method.invoke(this.getMapper(), record);
            if(reObj instanceof Integer) {
                rows = (Integer)reObj;
                if(rows > 0) {
                    Long id = ReflectionUtil.getFieldValueById("id", record);
                    redisUtil.del(record.getClass().getName() + id);
                    this.clearRelatedCache(record);
                    return rows;
                }
            }
        } catch (Exception e) {
            logger.debug("deleteByPrimaryKey失败" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return rows;
    }

    /**
     * 插入方法
     * @param record
     * @return
     */
    public int insertSelective(V record) {
        Method method = this.getMethod("insertSelective", record);
        int rows = 0;
        try {
            Object reObj = method.invoke(this.getMapper(), record);
            if(reObj instanceof Integer) {
                rows = (Integer)reObj;
                if(rows > 0) {
                    Long id = ReflectionUtil.getFieldValueById("id", record);
                    redisUtil.set(record.getClass().getName() + id, record, this.getCacheTime());
                    this.clearRelatedCache(record);
                    return rows;
                }
            }
        } catch (Exception e) {
            logger.debug("insertSelective失败" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return rows;
    }

    /**
     * 根据id获得
     * @param id
     * @return
     */
    public V selectByPrimaryKey(Long id) {
        Method method = this.getMethod("selectByPrimaryKey", id);
        V reObj = (V)this.redisUtil.get(this.entityClass.getName() + id);
        if(reObj == null) {
            try {
                reObj = (V)method.invoke(this.getMapper(), id);
            } catch  (Exception e) {
                logger.debug("selectByPrimaryKey失败" + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
            if(reObj != null) {
                redisUtil.set(this.entityClass.getName() + id, reObj, this.getCacheTime());
            }
        }
        return reObj;
    }

    /**
     * 修改方法
     * @param record
     * @return
     */
    public int updateByPrimaryKeySelective(V record) {
        Method method = this.getMethod("updateByPrimaryKeySelective", record);
        int rows = 0;
        try {
            Object reObj = method.invoke(this.getMapper(), record);
            if(reObj instanceof Integer) {
                rows = (Integer)reObj;
                if(rows > 0) {
                    Long id = ReflectionUtil.getFieldValueById("id", record);
                    redisUtil.set(record.getClass().getName() + id, record, this.getCacheTime());
                    this.clearRelatedCache(record);
                    return rows;
                }
            }
        } catch (Exception e) {
            logger.debug("updateByPrimaryKeySelective失败" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return rows;
    }

    public int countByExample(K example) {
        Method method = this.getMethod("countByExample", example);
        int rows = 0;
        try {
            Object reObj = method.invoke(this.getMapper(), example);
            if(reObj instanceof Integer) {
                rows = (Integer)reObj;
            }
        } catch (Exception e) {
            logger.debug("countByExample失败" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return rows;
    }

    public List<V> selectByExample(K example) {
        Method method = this.getMethod("selectByExample", example);
        List<V> reList = null;
        try {
            reList = (List<V>)method.invoke(this.getMapper(), example);
        } catch (Exception e) {
            logger.debug("selectByExample失败" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return reList;
    }
}
