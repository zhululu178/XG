package com.letsup.habit.app.backend.dao.cache;

import com.letsup.habit.app.backend.HabApplicationTest;
import com.letsup.habit.app.backend.dao.entity.HabAppFamilyMember;
import com.letsup.habit.app.backend.dao.entity.HabAppHabit;
import io.jsonwebtoken.lang.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HabAppHabitMapperCacheTest extends HabApplicationTest {
    @Autowired
    HabAppHabitMapperCache habAppHabitMapperCache;

    @Test
    public void getSelectByid() {
        List<HabAppHabit> list = habAppHabitMapperCache.getFromTodayOnHabitByFamilyId(32l, "2019-12-17");
        Assert.notNull(list);
    }
}
