package com.framework.user.auth;


import com.alibaba.fastjson.JSONObject;
import com.framework.common.constants.ErrorCode;
import com.framework.common.domain.ErrorMessage;
import com.framework.core.util.I18nUtils;
import com.framework.user.model.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 没用了
 * 判断session 是否过期
 */
public class TokenCheckerFilter extends AccessControlFilter {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    public TokenCheckerFilter() {
    }

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        if(httpServletRequest.getSession() != null && httpServletRequest.getSession().getAttribute("user") != null) {
            SysUserEntity userInfo = (SysUserEntity)httpServletRequest.getSession().getAttribute("user");
            if(SecurityUtils.getSubject().getPrincipal() == null) {
                BaseUsernamePasswordToken token = new BaseUsernamePasswordToken(userInfo.getAccount(), userInfo.getPassword());
                SecurityUtils.getSubject().login(token);
            }

            return true;
        } else {
            this.sessionRedirect(request, response);
            return false;
        }
    }

    private void sessionRedirect(ServletRequest request, ServletResponse response) throws Exception {
        if(((HttpServletRequest)request).getHeader("x-auth-token") == null) {
            Cookie res = new Cookie("my_app_auth_session", "");
            res.setMaxAge(2);
            res.setPath("/");
            ((HttpServletResponse)response).addCookie(res);
        }

        HttpServletResponse res1 = WebUtils.toHttp(response);
        String errorMsg = I18nUtils.getMessage(ErrorCode.Common.sessionTimeout);
        if(errorMsg == null) {
            errorMsg = "会话已过期,请刷新页面重新登录！";
        }

        String result = JSONObject.toJSON(new ErrorMessage("000-B-" + ErrorCode.Common.sessionTimeout.getCode(), errorMsg)).toString();
        res1.setStatus(401);
        res1.setContentType("application/json");
        res1.getWriter().write(result);
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }
}
