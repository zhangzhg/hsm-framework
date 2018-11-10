package com.framework.core.service;

import com.framework.common.domain.RedisMessage;

/**
 * redis pub/sub服务接口
 * 
 */
public interface IRedisPubSubService {

	/**
	 * 发布消息
	 * 
	 * @param redisMessage
	 */
	public void publish(RedisMessage redisMessage);

}
