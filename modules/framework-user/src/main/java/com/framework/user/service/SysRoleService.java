package com.framework.user.service;

import com.framework.user.model.SysRoleEntity;

import java.util.List;

public interface SysRoleService {
    List<SysRoleEntity> queryRoleByUserId(String id);
}
