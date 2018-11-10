package com.framework.eventbus.configuration;

import com.framework.eventbus.InnoSubscriberExceptionHandler;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionHandler;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

/**
 * 事件总线配置类
 *
 * <code>
        // 自动注入eventBus
        @Autowired
        private EventBus eventBus;

        // 声明事件监听器
        @Component
        public class ChapterTextChangedListener {
            @Autowired
            private EventBus eventBus;

            //订阅事件
            @Subscribe
            @AllowConcurrentEvents
            public void changeChapterText(ChapterTextChangedEvent event) {
                // do something...
            }

            @PostConstruct
            public void registerListener() {
                eventBus.register(this);
            }

            @PreDestroy
            public void unregisterListener() {
                eventBus.unregister(this);
            }
        }

        // 声明事件bean
        public class ChapterTextChangedEvent {
            private String euid;
            private String originalText;
            private String newText;
            private String vid;
            private String projectId;

            //...getter setter
        }

        // 发布事件
        // 发布标题变更事件
        eventBus.post(new ChapterTextChangedEvent(parentTitle.getId(), originalText, newText, view.getVid(), view.getProjectId()));
 * </code>
 */
@Configuration
public class EventBusConfig {
    private static final SubscriberExceptionHandler EXCEPTION_HANDLER = new InnoSubscriberExceptionHandler();

    @Bean(name = "eventBus", autowire = Autowire.BY_NAME)
    public EventBus createEventBus() {
        return new EventBus(EXCEPTION_HANDLER);
    }

    @Bean
    public AsyncEventBus createAsyncEventBus() {
        return new AsyncEventBus(Executors.newWorkStealingPool(), EXCEPTION_HANDLER);
    }

}
