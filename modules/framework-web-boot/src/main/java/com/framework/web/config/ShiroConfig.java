package com.framework.web.config;

import com.framework.user.auth.RedisUserRealm;
import com.framework.user.model.RedisCache;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.cache.RedisCacheManager;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 部署多台机器的时候，不能用ehcache本地缓存，达不到session共享的目的。
 * 备注：如果用ehcache的话，部署多台，就要配置代理策略，同一个ip只能代理到同一台服务器，因为一个用户的缓存只保存在单独的某台上。
 * 手机app没有权限验证，不知道这个是否合理？
 */
@Configuration
@Order(3)
public class ShiroConfig {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /****
     * 自定义Real
     */
    @Bean
    public RedisUserRealm shiroRealm() {
        RedisUserRealm realm = new RedisUserRealm();
        // 根据情况使用缓存器
        realm.setCacheManager(new ShiroRedisCacheManager(redisTemplate));
        return realm;
    }

    /***
     * 安全管理配置
     *
     * @return
     */
    @Bean
    public SecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 配置
        securityManager.setRealm(shiroRealm());
        // 注意这里必须配置securityManager
        SecurityUtils.setSecurityManager(securityManager);
        //设置session可以交给redis，也可以存本地， 无关。如果配置的话，将会实现session共享（类似单点登录）。
        //securityManager.setSessionManager();
        // 根据情况选择缓存器
        securityManager.setCacheManager(new ShiroRedisCacheManager(redisTemplate));//shiroEhCacheManager()

        return securityManager;
    }



    private class ShiroRedisCacheManager implements CacheManager {
        RedisTemplate template;

        public ShiroRedisCacheManager(RedisTemplate template) {
            this.template = template;
        }

        @Override
        public <K, V> org.apache.shiro.cache.Cache<K, V> getCache(String name) throws CacheException {
            return new RedisCache<>(name, template);
        }
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();

        filterFactoryBean.setSecurityManager(defaultWebSecurityManager());

        //在app下不要这个东西，后续需要兼容，要改变一下
        filterFactoryBean.setLoginUrl("/");
        filterFactoryBean.setSuccessUrl("/index/home");

        //设置url全部需要验证才能登陆
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/login/**","anon");
        filterChainDefinitionMap.put("/code","anon");
        filterChainDefinitionMap.put("/logout","logout");
        filterChainDefinitionMap.put("/**", "authc");
        filterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return filterFactoryBean;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
