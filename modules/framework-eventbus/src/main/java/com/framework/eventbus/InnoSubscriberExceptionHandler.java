package com.framework.eventbus;

import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 事件总线订阅异常处理
 *
 */
public class InnoSubscriberExceptionHandler implements SubscriberExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(InnoSubscriberExceptionHandler.class);

    @Override
    public void handleException(Throwable exception, SubscriberExceptionContext context) {
        LOGGER.error(exception.getMessage(), exception);
    }
}
