package com.letsup.habit.app.backend.dao.cache;

import com.letsup.habit.app.backend.HabApplicationTest;
import com.letsup.habit.app.backend.dao.entity.HabAppFamily;
import com.letsup.habit.app.backend.dao.entity.HabAppFamilyMember;
import com.letsup.habit.app.backend.dao.mapper.HabAppFamilyMapperExt;
import com.letsup.habit.app.backend.dao.mapper.HabAppFamilyMemberMapperExt;
import io.jsonwebtoken.lang.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class HabAppFamilyMapperCacheTest extends HabApplicationTest {
    @Autowired
    HabAppFamilyMemberMapperCache habAppFamilyMemberMapperCache;

    @Test
    public void getSelectByid() {
        HabAppFamilyMember familyMember = new HabAppFamilyMember();
        familyMember.setId(4l);
        int rows = habAppFamilyMemberMapperCache.deleteByPrimaryKey(familyMember);
        Assert.notNull(rows);
    }
}
