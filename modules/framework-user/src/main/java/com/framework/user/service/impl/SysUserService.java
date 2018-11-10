package com.framework.user.service.impl;

import com.framework.user.mapper.UserMapper;
import com.framework.user.model.SysUserEntity;
import com.framework.user.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysUserService")
public class SysUserService implements ISysUserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public SysUserEntity getSysUserByAccount(String username) {
        return userMapper.find(username);
    }
}
