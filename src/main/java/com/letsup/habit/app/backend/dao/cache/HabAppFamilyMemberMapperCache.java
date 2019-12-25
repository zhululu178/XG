package com.letsup.habit.app.backend.dao.cache;

import com.letsup.habit.app.backend.dao.entity.HabAppFamilyMember;
import com.letsup.habit.app.backend.dao.entity.HabAppFamilyMemberExample;
import com.letsup.habit.app.backend.dao.mapper.HabAppFamilyMemberMapperExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class HabAppFamilyMemberMapperCache extends BaseMapperCache<HabAppFamilyMember, HabAppFamilyMemberExample> implements HabAppFamilyMemberMapperExt {
    @Autowired
    private HabAppFamilyMemberMapperExt habAppFamilyMemberMapperExt;

    @Override
    protected long getCacheTime() {
        //60*60*24=86400 1天
        return 86400;
    }

    @Override
    protected Object getMapper() {
        return this.habAppFamilyMemberMapperExt;
    }
}
