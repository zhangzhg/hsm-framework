package com.framework.user.service.impl;

import com.framework.user.mapper.RoleMapper;
import com.framework.user.model.SysRoleEntity;
import com.framework.user.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleService implements ISysRoleService {
    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<SysRoleEntity> queryRoleByUserId(String userId) {
        return roleMapper.findByUserId(userId);
    }
}
