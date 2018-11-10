package com.framework.user.service;

import com.framework.user.model.SysUserEntity;

public interface ISysUserService {
    SysUserEntity getSysUserByAccount(String username);
}
