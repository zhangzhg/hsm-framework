package com.framework.user.auth;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.List;

public class TokenFormAuthenticationFilter extends FormAuthenticationFilter {
    public static final String tokenKey = "token-all";
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String token = request.getParameter("token");
        boolean access;
        if (StringUtils.isBlank(token)) {
            access = super.onAccessDenied(request, response);
        } else {
            //这边的值是登录的时候产生的
            List<String> list = (List<String>) redisTemplate.opsForValue().get(tokenKey);
            if (!ObjectUtils.isEmpty(list) && list.contains(token)) {
                return true;
            }

            access = super.onAccessDenied(request, response);
        }

        return access;

    }
}
