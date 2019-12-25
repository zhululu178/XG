package com.letsup.habit.app.backend.service.impl;

import com.letsup.habit.app.backend.dao.entity.HabAppRole;
import com.letsup.habit.app.backend.dao.entity.HabAppRoleExample;
import com.letsup.habit.app.backend.dao.mapper.HabAppRoleMapperExt;
import com.letsup.habit.app.backend.service.HabRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabRoleServiceImpl implements HabRoleService {
    @Autowired
    private HabAppRoleMapperExt habAppRoleMapperExt;

    @Override
    public List<HabAppRole> getByUserId(Long userId) {
        return this.habAppRoleMapperExt.getByUserId(userId);
    }

    @Override
    public List<HabAppRole> getAllRoles() {
        return habAppRoleMapperExt.selectByExample(new HabAppRoleExample());
    }
}