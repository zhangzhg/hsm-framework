package com.framework.user.service;

import com.framework.user.model.SysMenuEntity;
import com.framework.user.model.SysRoleEntity;

import java.util.List;

public interface ISysMenuService {
    List<SysMenuEntity> queryMenu(List<SysRoleEntity> roles);
}
