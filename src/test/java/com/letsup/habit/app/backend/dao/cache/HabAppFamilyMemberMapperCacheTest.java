package com.letsup.habit.app.backend.dao.cache;

import com.letsup.habit.app.backend.HabApplicationTest;
import com.letsup.habit.app.backend.dao.entity.HabAppFamilyMember;
import com.letsup.habit.app.backend.dao.entity.HabAppFamilyMemberExample;
import io.jsonwebtoken.lang.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HabAppFamilyMemberMapperCacheTest extends HabApplicationTest {
    @Autowired
    HabAppFamilyMemberMapperCache habAppFamilyMemberMapperCache;

    @Test
    public void getSelectByid() {
        HabAppFamilyMember member = habAppFamilyMemberMapperCache.selectByPrimaryKey(57l);
        Assert.notNull(member);
    }

    @Test
    public void getInsert() {
        HabAppFamilyMember member = new HabAppFamilyMember();
        member.setVersion(0l);
        member.setIsChild("N");
        member.setName("Test测试" + System.currentTimeMillis());
        habAppFamilyMemberMapperCache.insertSelective(member);
        Assert.notNull(member);
    }

    @Test
    public void getUpdate() {
        HabAppFamilyMember member = this.habAppFamilyMemberMapperCache.selectByPrimaryKey(142l);
        member.setSex("M");
        member.setRole("MOTHER");
        member.setAvatar("assets/images/mum.png");
        habAppFamilyMemberMapperCache.updateByPrimaryKeySelective(member);
        Assert.notNull(member);
    }

    @Test
    public void getDelete() {
        HabAppFamilyMember member = this.habAppFamilyMemberMapperCache.selectByPrimaryKey(142l);
        habAppFamilyMemberMapperCache.deleteByPrimaryKey(member);
        Assert.notNull(member);
    }

    @Test
    public void getCountExample() {
        HabAppFamilyMemberExample example = new HabAppFamilyMemberExample();
        example.createCriteria().andIsDeletedEqualTo("n");
        int member = this.habAppFamilyMemberMapperCache.countByExample(example);
        Assert.notNull(member);
    }

    @Test
    public void getSelectExample() {
        HabAppFamilyMemberExample example = new HabAppFamilyMemberExample();
        example.createCriteria().andIsDeletedEqualTo("n").andIdEqualTo(142l);
        List<HabAppFamilyMember> members = this.habAppFamilyMemberMapperCache.selectByExample(example);
        Assert.notNull(members);
    }
}
