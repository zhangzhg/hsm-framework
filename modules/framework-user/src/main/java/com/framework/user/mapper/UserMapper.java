package com.framework.user.mapper;

import com.framework.user.model.SysUserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface UserMapper {
    SysUserEntity find(@Param("account") String account);

    String findId(Map map);
}
