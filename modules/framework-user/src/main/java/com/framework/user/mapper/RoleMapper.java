package com.framework.user.mapper;

import com.framework.user.model.SysRoleEntity;

import java.util.List;

public interface RoleMapper {
    List<SysRoleEntity> findByUserId(String userId);
}