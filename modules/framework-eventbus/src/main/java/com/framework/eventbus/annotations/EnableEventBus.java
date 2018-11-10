package com.framework.eventbus.annotations;

import com.framework.eventbus.configuration.EventBusConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 开启事件总线特性的注解
 *
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = {java.lang.annotation.ElementType.TYPE})
@Documented
@Import(EventBusConfig.class)
public @interface EnableEventBus {
}
