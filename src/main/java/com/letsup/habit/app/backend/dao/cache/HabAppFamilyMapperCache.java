package com.letsup.habit.app.backend.dao.cache;

import com.letsup.habit.app.backend.dao.entity.HabAppFamily;
import com.letsup.habit.app.backend.dao.entity.HabAppFamilyExample;
import com.letsup.habit.app.backend.dao.mapper.HabAppFamilyMapperExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class HabAppFamilyMapperCache extends BaseMapperCache<HabAppFamily, HabAppFamilyExample> implements HabAppFamilyMapperExt {
    @Autowired
    private HabAppFamilyMapperExt habAppFamilyMapperExt;

    @Override
    protected long getCacheTime() {
        //60*60*24=86400 1天
        return 2592000;
    }

    @Override
    protected Object getMapper() {
        return this.habAppFamilyMapperExt;
    }
}
