package com.framework.user.service;

import com.framework.user.model.SysMenuEntity;

import java.util.List;

public interface ISysMenuService {
    List<SysMenuEntity> queryMenuByRoleIds(List<String> roleIds);
}
