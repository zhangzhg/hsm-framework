package com.framework.user.service.impl;

import com.framework.user.mapper.RoleMenuMapper;
import com.framework.user.model.SysMenuEntity;
import com.framework.user.model.SysRoleEntity;
import com.framework.user.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysMenuService implements ISysMenuService {
    @Autowired
    RoleMenuMapper roleMenuMapper;

    @Override
    public List<SysMenuEntity> queryMenu(List<SysRoleEntity> roles) {
        List<String> roleIds = getRoleIdList(roles);
        return roleMenuMapper.queryMenuByRoleIds(roleIds);
    }

    private List<String> getRoleIdList(List<SysRoleEntity> roleList){
        List<String> roles = new ArrayList<String>();
        if(roleList != null && roleList.size() > 0){
            roles.addAll(roleList.stream().map(SysRoleEntity::getId).collect(Collectors.toList()));
        }
        return roles;
    }
}
