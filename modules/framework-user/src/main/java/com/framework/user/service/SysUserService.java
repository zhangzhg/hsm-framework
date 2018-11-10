package com.framework.user.service;

import com.framework.user.model.SysUserEntity;

public interface SysUserService {
    SysUserEntity getSysUserByAccount(String username);
}
