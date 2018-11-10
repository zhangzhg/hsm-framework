package com.framework.core.message.listener;

/**
 * Redis主题消息监听接口
 */
public interface RedisTopicMessageListener<T> {

    /**
     * 接收消息触发回调函数
     * @param message       消息
     */
    void onMessage(T message);
}
