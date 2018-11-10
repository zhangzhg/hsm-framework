package com.framework.user.auth;

import com.framework.user.constant.DicDataEnum;
import com.framework.user.model.SysMenuEntity;
import com.framework.user.model.SysRoleEntity;
import com.framework.user.model.SysUserEntity;
import com.framework.user.service.ISysMenuService;
import com.framework.user.service.ISysRoleService;
import com.framework.user.service.ISysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RedisUserRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(RedisUserRealm.class);

    @Autowired
    ISysUserService sysUserService;

    @Autowired
    ISysRoleService sysRoleService;

    @Autowired
    ISysMenuService sysMenuService;

    public RedisUserRealm() {
        super();
        // 设置认证token的实现类
        setAuthenticationTokenClass(BaseUsernamePasswordToken.class);
        // 设置加密算法
        setCredentialsMatcher(new HashedCredentialsMatcher(Md5Hash.ALGORITHM_NAME)); //MD5加密
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUserEntity userInfo = (SysUserEntity) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //1.角色
        List<SysRoleEntity> roleList = sysRoleService.queryRoleByUserId(userInfo.getId());
        List<String> roleCodes = getRoleCodeList(roleList);
        info.addRoles(roleCodes);
        if(!ObjectUtils.isEmpty(roleCodes)){
            List<SysMenuEntity> permissionList = sysMenuService.queryMenu(roleList);
            List<String> permissions = getPermissionList(permissionList);
            info.addStringPermissions(permissions);
        }
        return info;
    }

    private List<String> getPermissionList( List<SysMenuEntity> permissionList){
        List<String> menus = new ArrayList<String>();
        if(permissionList != null && permissionList.size() > 0){
            menus.addAll(permissionList.stream().map(SysMenuEntity::getCode).collect(Collectors.toList()));
        }
        return menus;
    }

    private List<String> getRoleCodeList( List<SysRoleEntity> roleList){
        List<String> roles = new ArrayList<String>();
        if(roleList != null && roleList.size() > 0){
            roles.addAll(roleList.stream().map(SysRoleEntity::getCode).collect(Collectors.toList()));
        }
        return roles;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        BaseUsernamePasswordToken token = (BaseUsernamePasswordToken) authToken;
        SysUserEntity user = sysUserService.getSysUserByAccount(token.getUsername());
        if (user != null) {
            if (DicDataEnum.userLocked.getIntId().equals(user.getStatus())) {
                throw new LockedAccountException("账号被锁定");
            }

            //加密的话可以加salt
            return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        } else {
            throw new UnknownAccountException("账号密码错误");
        }
    }

}
