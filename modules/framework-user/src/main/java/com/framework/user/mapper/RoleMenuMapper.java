package com.framework.user.mapper;

import com.framework.user.model.SysMenuEntity;

import java.util.List;

public interface RoleMenuMapper {
    List<SysMenuEntity> queryMenuByRoleIds(List<String> roleIds);
}
