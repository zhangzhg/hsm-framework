package com.framework.user.listener;

import com.framework.common.util.BeanUtils;
import com.framework.common.util.ParamUtils;
import com.framework.common.util.SpringUtils;
import com.framework.common.util.StringUtils;
import com.framework.core.listener.ICreateListenable;
import com.framework.core.listener.IModifyListenable;
import com.framework.core.listener.IModifyUpdatableListenable;
import com.framework.user.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 这个方法是用来做数据插入的时候，自动设置更新时间， 创建时间。
 * 但是不建议这么使用。因为每次更新或者新增对象都会跑这个监听器，降低效率
 */
public class EntityEventListener {

    private static final Logger logger = LoggerFactory.getLogger(EntityEventListener.class);

    private String getCurrentUserId() {

        String currentUserId = "";
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            Object user = attributes.getRequest().getSession().getAttribute("user");

            if (user != null) {
                Map map = BeanUtils.convertBean(user);
                if (!StringUtils.isEmpty(user)) {
                    currentUserId = (String) map.get("id");
                }
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            //获取配置的systemUserAccount查询获得id
            currentUserId = getSystemUserAccount();
        }

        return currentUserId;
    }

    /**
     * 这个写法有点诡异， 用户id居然从环境变量配置里面去取。
     * 估计是用来防止初始化系统是没有用户做出错处理的
     */
    private String getSystemUserAccount() {
        String currentUserId = "";
        try{
            Object obj = SpringUtils.getBean("app.core.CONFIGURATION_PROPERTIES");
            String systemUserAccount = (String) BeanUtils.convertBean(obj).get("systemUserAccount");
            Map map = new HashMap();
            map.put("account", systemUserAccount);
            UserMapper userIdMapper = SpringUtils.getBean("userMapper");
            currentUserId = userIdMapper.findId(map);
        }
        catch (Exception ex){
            logger.error(ex.getMessage());
        }
        return currentUserId;
    }

    /**
     * 创建实体前触发事件
     */
    @PrePersist
    public void prePersist(Object entity) {
        if (entity instanceof ICreateListenable) {
            if (ParamUtils.isNull(((ICreateListenable) entity).getCreatedTime()))
                ((ICreateListenable) entity).setCreatedTime(new Date(System.currentTimeMillis()));
            if (ParamUtils.isEmpty(((ICreateListenable) entity).getCreatorId()))
                ((ICreateListenable) entity).setCreatorId(getCurrentUserId());
        }
    }

    /**
     * 修改实体前触发事件
     */
    @PreUpdate
    public void preUpdate(Object entity) {
        if (entity instanceof IModifyListenable && entity instanceof IModifyUpdatableListenable) {
            if (((IModifyUpdatableListenable) entity).getModifyUpdatable()) {
                ((IModifyListenable) entity).setModifiedTime(new Date(System.currentTimeMillis()));
                ((IModifyListenable) entity).setModifierId(getCurrentUserId());
            }
        } else if (entity instanceof IModifyListenable) {
            ((IModifyListenable) entity).setModifiedTime(new Date(System.currentTimeMillis()));
            ((IModifyListenable) entity).setModifierId(getCurrentUserId());
        }

    }

}
