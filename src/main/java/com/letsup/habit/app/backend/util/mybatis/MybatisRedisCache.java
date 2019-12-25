package com.letsup.habit.app.backend.util.mybatis;

import com.letsup.habit.app.backend.util.JsonUtils;
import com.letsup.habit.app.backend.util.RedisUtil;
import com.letsup.habit.app.backend.util.spring.ApplicationContextHolder;
import org.apache.ibatis.cache.Cache;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Mybatis - redis二级缓存
 *
 * @author luohq
 * @date 2019/3/15
 */
public final class MybatisRedisCache implements Cache {
    /**
     * 日志工具类
     */
    private static final Logger logger = LogManager.getLogger(MybatisRedisCache.class);

    /**
     * 读写锁
     */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    /**
     * ID
     */
    private String id;

    /**
     * 集成redisTemplate
     */
    private static RedisUtil redisUtil;

    private MybatisRedisCache() {
    }

    public MybatisRedisCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        } else {
            logger.debug("MybatisRedisCache.id=" + id);
            this.id = id;
        }
    }


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public int getSize() {
        try {
            Long size = this.getRedisUtil().hSize(this.id.toString());
            logger.debug("MybatisRedisCache.getSize: {" + id + "}->{" + size + "}");
            return size.intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void putObject(final Object key, final Object value) {
        try {
            logger.debug("MybatisRedisCache.putObject: {" + id + "}->{" + key + "}->{" + JsonUtils.objectToJson(value) + "}");
            this.getRedisUtil().hset(this.id.toString(), key.toString(), value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getObject(final Object key) {
        try {
            Object hashVal = this.getRedisUtil().hget(this.id.toString(), key.toString());
            logger.debug("MybatisRedisCache.getObject: {" + id + "}->{" + key + "}->{" + JsonUtils.objectToJson(hashVal) + "}");
            return hashVal;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object removeObject(final Object key) {
        try {
            this.getRedisUtil().hdel(this.id.toString(), key.toString());
            logger.debug("MybatisRedisCache.removeObject:  {" + id + "}->{" + key + "}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void clear() {
        try {
            this.getRedisUtil().del(this.id.toString());
            logger.debug("MybatisRedisCache.clear:" + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

    @Override
    public String toString() {
        return "MybatisRedisCache {" + this.id + "}";
    }

    private RedisUtil getRedisUtil() {
        if (redisUtil == null) {
            redisUtil = ApplicationContextHolder.getBean("redisUtil");
        }
        return redisUtil;
    }
}
