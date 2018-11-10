package com.framework.user.service.impl;

import com.framework.user.mapper.RoleMenuMapper;
import com.framework.user.model.SysMenuEntity;
import com.framework.user.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMenuService implements ISysMenuService {
    @Autowired
    RoleMenuMapper roleMenuMapper;

    @Override
    public List<SysMenuEntity> queryMenuByRoleIds(List<String> roleIds) {
        return roleMenuMapper.queryMenuByRoleIds(roleIds);
    }
}
