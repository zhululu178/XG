package com.letsup.habit.app.backend.service;

import com.letsup.habit.app.backend.dao.entity.HabAppRole;

import java.util.List;

public interface HabRoleService {

    List<HabAppRole> getByUserId(Long id);

    List<HabAppRole> getAllRoles();
}
