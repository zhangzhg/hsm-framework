#1. 事件总线模块

##1.1. 概述
　　引入谷歌的EventBus进行进程内通信, 同步的使用EventBus, 异步的使用AsyncEventBus。

　　事件总线的事件机制能有效解耦业务模块之间的耦合, 核心业务模块发布事件, 不同的模块业务上区隔, 但有部分数据的交叉, 通过事件总线, 能有效隔离, 防止特殊的业务模块因IO耗时等影响核心业务模块的正常执行。

　　EventBus基于进程内通信, 所以性能和可靠性较好, 无基础设施故障(数据库宕机等)或无代码错误(QA没测试出来)的情况下不会出现不一致的情况。若出现不一致情况, 系统日志中会打印错误日志, 并且可以通过业务操作进行弥补。

　　例如, 需求维护单关联设计件中的功能点的标题并在数据库表中进行冗余, 正常情况下都能正确通知更新到冗余的标题文本。若维护单的监听并更新冗余的代码有隐藏错误情况, 并被触发会导致更新失败, 但是设计件的标题变更行为还是会成功。 弥补的办法就是删掉维护单对功能点的关联重新关联或者再次修改标题。

　　更好的方案是采用支持持久化的事件总线, 订阅方发生异常也即是消费失败不会修改持久记录中的消费状态, 待系统重启或恢复正常重新消费成功后才置为已消费状态。但当前的开发资源有限, 不会投入资源在持久化事件总线的研究上。

##1.2. 注意事项
  - 订阅事件的方法必须只有一个参数, 否则抛出IllegalArgumentException
  - 订阅事件根据参数的类型来触发
  - 订阅回调函数中抛出异常都会被抑制不会传导到post也就是发出事件的主线程, 双方处于两个独立的事务
##1.3. 使用方法

        // maven 依赖
        <dependency>
            <groupId>com.evada</groupId>
            <artifactId>inno-eventbus</artifactId>
            <version>${inno-framework.version}</version>
        </dependency>
        
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
