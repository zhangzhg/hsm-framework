package com.framework.core.listener;

import java.util.Date;

/**
 * 创建者和创建时间可监听
 * （通过监听器自动设置值）
 */
public interface ICreateListenable {
    /** 设置创建者 */
    public void setCreatorId(String userId);

    /** 设置创建时间 */
    public void setCreatedTime(Date now);
    
    /** 获取创建者*/
    public String getCreatorId();
    
    /** 获取创建时间*/
    public Date getCreatedTime();
}
