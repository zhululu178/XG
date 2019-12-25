package com.letsup.habit.app.backend.util;

import com.letsup.habit.app.backend.HabApplicationTest;
import com.letsup.habit.app.backend.vo.HabitDailyBonusVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisUtilTest extends HabApplicationTest {
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testPush() {
        HabitDailyBonusVo vo = new HabitDailyBonusVo();
        vo.setBonus(100);
        HabitDailyBonusVo vo1 = new HabitDailyBonusVo();
        vo1.setBonus(200);
        HabitDailyBonusVo vo2 = new HabitDailyBonusVo();
        vo2.setBonus(300);
        boolean result = this.redisUtil.lSet("testList33", vo);
        System.out.println(result);
        result = this.redisUtil.lSet("testList33", vo1);
        System.out.println(result);
        result = this.redisUtil.lSet("testList33", vo2);
        System.out.println(result);
    }
    @Test
    public void testPop() {
        Object value1 = this.redisUtil.rifhtPop("testList33");
        Assert.assertNull(value1);

    }

}
