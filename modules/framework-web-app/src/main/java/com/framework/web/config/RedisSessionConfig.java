package com.framework.web.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.common.util.Base64Utils;
import com.framework.web.http.InnoHttpSessionStrategy;
import com.framework.web.http.TokenHttpSessionStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieHttpSessionStrategy;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;

/**
 * spring-session会话配置
 *
 */
@Configuration
@EnableRedisHttpSession
@EnableCaching
public class RedisSessionConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisSessionConfig.class);

	@Value("${server.session-timeout}")
	private int maxInactiveIntervalInSeconds;

	@Autowired
	protected RedisProperties properties;

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName(this.properties.getHost());
		factory.setPort(this.properties.getPort());
		if (this.properties.getPassword() != null) {
			String password = properties.getPassword();
			try{
				password =  Base64Utils.getFromBase64(properties.getPassword());
			}
			catch (Exception ex){
				LOGGER.error("jedisConnectionFactory config password base64 decode failure!");
			}
			finally {
				factory.setPassword(password);
			}
		}
		factory.setDatabase(this.properties.getDatabase());
		if (this.properties.getTimeout() > 0) {
			factory.setTimeout(this.properties.getTimeout());
		}
		factory.setUsePool(true);
		factory.setPoolConfig(jedisPoolConfig());
		return factory;
	}

	private JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
		RedisProperties.Pool props = this.properties.getPool();
		config.setMaxTotal(props.getMaxActive());
		config.setMaxIdle(props.getMaxIdle());
		config.setMinIdle(props.getMinIdle());
		config.setMaxWaitMillis(props.getMaxWait());
		config.setTestOnBorrow(false);
		config.setTestOnReturn(false);
		config.setTimeBetweenEvictionRunsMillis(-1);
		config.setLifo(true);
		return config;
	}


	@Primary
	@Bean
	public RedisOperationsSessionRepository sessionRepository(RedisOperations<Object, Object> sessionRedisTemplate) {
		RedisOperationsSessionRepository sessionRepository = new RedisOperationsSessionRepository(sessionRedisTemplate);
		sessionRepository.setDefaultMaxInactiveInterval(maxInactiveIntervalInSeconds);
		return sessionRepository;
	}

	@Bean
	public KeyGenerator cacheKeyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method,
								   Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};

	}

	@Bean
	public CacheManager cacheManager(
			@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
		RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
		//设置redis 缓存默认的过期时间为24小时
		redisCacheManager.setDefaultExpiration(24 * 60 * 60);
		return redisCacheManager;
	}

	/**
	 * 注入redisTemplate
	 *
	 * @param factory
	 * @return
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(
			RedisConnectionFactory factory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(factory);
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
				Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.afterPropertiesSet();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new GenericToStringSerializer<>(Object.class));
		return redisTemplate;
	}

	@Bean
	public HttpSessionStrategy httpSessionStrategy() {
		CookieHttpSessionStrategy cookieHttpSessionStrategy = new CookieHttpSessionStrategy();
		HeaderHttpSessionStrategy headerHttpSessionStrategy = new HeaderHttpSessionStrategy();
		TokenHttpSessionStrategy ticketHttpSessionStrategy = new TokenHttpSessionStrategy();
		InnoHttpSessionStrategy innoHttpSessionStrategy = new InnoHttpSessionStrategy(
				cookieHttpSessionStrategy, headerHttpSessionStrategy, ticketHttpSessionStrategy);
		return innoHttpSessionStrategy;
	}

	/**
	 * 没有使用先注释掉
	 * redis消息监听容器
	 *
	 * @param connectionFactory
	 * @param redisWebSocketMessageListener
	 * @return
	 */
//	@Bean
//	RedisMessageListenerContainer container(
//			RedisConnectionFactory connectionFactory,
//			RedisWebSocketMessageListener redisWebSocketMessageListener) {
//		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//		container.setConnectionFactory(connectionFactory);
//		container.addMessageListener(
//				redisMessageListenerAdapter(redisWebSocketMessageListener),
//				new PatternTopic("inno.*"));
//		return container;
//	}

	/**
	 * redis消息监听适配器
	 *
	 * @param redisWebSocketMessageListener
	 * @return
	 */
//	@Bean
//	public MessageListenerAdapter redisMessageListenerAdapter(
//			RedisWebSocketMessageListener redisWebSocketMessageListener) {
//		MessageListenerAdapter adapter = new MessageListenerAdapter(
//				redisWebSocketMessageListener);
//		return adapter;
//	}

}
