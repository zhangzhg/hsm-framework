package com.framework.core.message.listener;

import java.util.List;

import com.framework.core.exception.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.framework.common.domain.RedisMessage;

/**
 * redis消息监听器
 * 没有使用先注解掉
 */
//@Component
public class RedisWebSocketMessageListener implements MessageListener {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	@Autowired
	private RedisTemplate<String, Object> redisMessageTemplate;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		RedisMessage redisMessage;
		try {
			redisMessage = (RedisMessage) redisMessageTemplate
							.getValueSerializer()
							.deserialize(message.getBody());
		} catch (SerializationException | SecurityException e) {
			throw new SystemException("Could Not Deserialize Object!", e);
		}
		Object content = redisMessage.getObject();
		List<String> userIds = redisMessage.getUserIds();
		String channel = redisMessage.getChannel();
		boolean isBroadcast = redisMessage.isBroadcast();
		if (!channel.startsWith("inno.")) {
			throw new SystemException("Unknown Channel, Please StartWith inno. !");
		}
		if (isBroadcast) {
			simpMessagingTemplate.convertAndSend("/topic/" + channel, content);
		} else {
			for (String userId : userIds) {
				simpMessagingTemplate.convertAndSendToUser(userId, "/queue/"
						+ channel, content);
			}
		}
	}

}
