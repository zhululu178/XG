package com.letsup.habit.app.backend.dao.cache;

import com.letsup.habit.app.backend.dao.entity.HabAppUser;
import com.letsup.habit.app.backend.dao.entity.HabAppUserExample;
import com.letsup.habit.app.backend.dao.mapper.HabAppUserMapperExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class HabAppUserMapperCache extends BaseMapperCache<HabAppUser, HabAppUserExample> implements HabAppUserMapperExt {
    @Autowired
    private HabAppUserMapperExt habAppUserMapperExt;

    @Override
    protected long getCacheTime() {
        //60*60*24=86400 1天
        return 2592000;
    }

    @Override
    protected Object getMapper() {
        return this.habAppUserMapperExt;
    }

    @Override
    public HabAppUser getUserByPhoneAndPassword(String phone, String password) {
        return this.habAppUserMapperExt.getUserByPhoneAndPassword(phone, password);
    }
}
