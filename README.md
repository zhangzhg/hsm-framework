# 1. framework平台基础框架 #

## 1.1. 平台介绍 ##

## 1.2. 模块说明 ##

### 1.2.1. framework-common ###
### 1.2.2. framework-core ###
### 1.2.3. framework-auth ###
### 1.2.4. framework-web-boot ###
问题：1、招果事务问题 未解决
     2、uuid
     3、createby modified 逻辑删除问题
     4、权限拦截器接口实现
     7、异常
     8、权限 接口 拦截器
     9、日期等特殊效验器
      11、hibernate日志没打印参数详细
      13、操作历史记录：新值，旧值
     14、业务操作日志（包含操作流水号）
     15、relationController后续改造 /sysRoleRelations/sysUsers
     16、DataDict改造，暂未整理字典为MenuType、Suffix.JPG
     17、mybatis jar 升级到3.3.0会引起流程org.activiti.engine.impl.persistence.entity.TaskEntity
     selectTaskByQueryCriteria报错出现重复orderby
### 1.3 技术特点 ###

### 1.3.0. mysql配置服务器时间 ###
 - 
### 1.3.1. CRUD示例  ###
 
### 1.3.2. dao层使用  ###
 - repository
 - mapper

### 1.3.2. 迎合REST风格  ###
    1、自定义@Rest注解代替@RequestMapping和@RestController
    例：
    @Rest(SysUser.class)
    public class SysUserController extends BaseController {
    }
    等同
    @RestController
    @RequestMapping("/sysUsers")
    public class SysUserController extends BaseController {
    }
    实现原理:注入自定义bean--requestMappingHandlerMapping,加工requestMappingInfo
    2、自定义MessageConverter适配JSON-API
    例：
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResultData  find(@RequestParam(required = true, defaultValue = "10") int size,
                            @RequestParam(required = true, defaultValue = "0") int page,
                            @RequestParam(required = false) String filter) {
        FilterParser filterParser = new FilterParser(SysUser.class,filter);
        Pageable pageable = new PageRequest(page, size);
        Page<SysUser> result = sysUserService.find(filterParser.toSearchFilters(), pageable);
        return new ResultData(SysUser.class, result);
    }
    json输出：
    {
      "sysUsers": [],
      "meta": {
        "total": 0,
        "totalPage": 0
      }
    }

### 1.3.2.1 mybatis分页查询扩展  ###
 - 1、定义切面mapper.find*,切面实现MybatisPaginationAspect
     <p>a、判断参数是否含有Pageable,有则使用分页插件PageHelper.startPage,由插件拦截器实现分页功能
     <p>b、对执行结果转成标准的Page对象
     <p>c、重写MapperProxy、MapperMethod解决mybatis多参数报错问题

### 1.3.2.2 JPA使用  ###
 - 定义Jpa接口：例如SysUserRepository
 - 自定义接口实现：SysUserRepositoryImpl
### 1.3.2.3 mybatis分页插件 ###
 - 分页插件依赖：
```xml  
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper</artifactId>
    <version>4.0.0</version>
</dependency>
```
 - 项目配置:inno-web-boot的resources/mybatis/mybatis-config.xml
 - 插件介绍:https://github.com/pagehelper/Mybatis-PageHelper
 
### 1.3.4. Bean Json序列化隐藏属性  ###

 - 方式1：@JsonIgnore
 
         /**
          * 用户实体
          * @author linliangkun
          * @date 15/7/23
          */
         @Entity
         @Table(name="sys_user")
         public class SysUser extends BaseModel{

             @JsonIgnore
             @Column
             private String name;
         }
 - 方式2：@JsonIgnoreProperties({"name"})

          /**
          **用户实体Json序列化时忽略name属性
          */
          @JsonIgnoreProperties({"name"})
          public class SysUserDTO {
               //姓名
               private String name;

               public String getName() {
                       return name;
               }

               public void setName(String name) {
                       this.name = name;
               }
          }
### 1.3.5. searchFilter使用 ###

 - 1、支持的运算
 
        a、运算符
        
            eq      =
            lt      <
            lte     <=
            gt      >
            gte     >=
            neq     !=
            like    like %abc%
            llike   like %abc
            rlike   like abc%
            nlike   not like
            inq     in(a,b,c)
            nin     not in(a,b,c)
            between between a,b
            
        b、逻辑运算符
        
            and 与
            or 或

 - 2、后端SearchFilter对象
 
        private String fieldName;   //条件属性名称
	    private Object value;   //值
	    private MatchType matchType;    //操作符
	    private Object[] values;    //支持传递数组
	    private LogicType logicType = LogicType.AND;    //默认and连接
	    private LogicType roundLogicType;   //如果连接的查询需要用扩号扩起来，则当成是该对象的一个子集joinFilters
	    private List<SearchFilter> joinFilters = new ArrayList<SearchFilter>(); //查询子集
	    
	    说明：
	    where password = '888888' and (sex != '2' or status = '1')
	    {"where":{"and":{"password":{"eq":"888888"},"or":{"sex":{"neq":"2"},"status":{"eq":"1"}}}}}
	    转换后的SearchFilter对象
	
	    List<SearchFilter> orFilters = new ArrayList<SearchFilter>();
	
	    SearchFilter passwordFilter = new SearchFilter("password", MatchType.EQ, "888888");
	    SearchFilter sexFilter = new SearchFilter("sex", MatchType.NEQ, "2");
	    SearchFilter statusFilter = new SearchFilter("status", MatchType.EQ, LogicType.OR, "1");
	    
	    orFilters.add(sexFilter);
	    orFilters.add(statusFilter);
	        
        passwordFilter.addRoundAndFilter(orFilters);

 - 3、fastjson使用
 
        由于json底层是用map存储，因此在解析产生JSONObject时是无顺序的。
        fastjson提供了Feature，可以保证解析后的顺序跟解析前一致。
        JSON.parseObject(string, Feature.OrderedField);
        

### 1.3.6. 业务缓存的使用 ###
 - 开启缓存配置

        @Configuration
        @EnableRedisHttpSession
        @EnableCaching
        public class RedisSessionConfig {
        }
 - 业务实现缓存  
  缓存必须指定key或keyGenerator，否则报SimpleKey无法转换成String的bug。

        @Service
        public class SysUserServiceImpl implements ISysUserService {
             public static Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

             @Autowired
             SysUserRepository sysUserRepository;

             /**
              * 使用业务缓存
              * value = "SysUser"   --业务根据实体自己定义
              * keyGenerator = "cacheKeyGenerator" --key生成规则
              * @return
              */
             @Cacheable(value = "sysUsers", keyGenerator = "cacheKeyGenerator")
             public List<SysUser> findAll() {
                logger.debug("没有缓存时进入此方法");
                return sysUserRepository.findAll();
             }
        }

### 1.3.8. 模块properties用法 ###
    - 1、在模块下 properties包下建对应的 *Properties类
      以bpm为例
      package com.evada.inno.bpm.properties;
      @ConfigurationProperties(prefix = "bpm",locations = "classpath:config/bpm.properties")
      public class BpmProperties {
          //定义属性
      }
    - 2、web模块的Application.java类增加注解@EnableConfigurationProperties({BpmProperties.class})
    - 3、web模块下增加resource/config/bpm.properties
    - 4、配置配置
         config/dev.properties增加bpm.upload_path=/innobpm/upload/workflow
         resource/config/bpm.properties增加bpm.upload_path=${bpm.upload_path}
    - 5、具体参考Bpm项目的BpmPropertiesTest单元测试
### 1.3.9. i18n国际化消息用法 ###
    1、配置 application.properties增加
        spring.messages.basename=i18n/messages
        spring.mvc.locale=zh_CN
        spring.messages.encoding=UTF-8
    2、springboot配置默认注入bean:MessageResouce
    3、消息的配置
       参见：common.constants.ErrorCode
       a、定义消息枚举key
       b、web/resources/i18n/messages_zh_CN.properties下定义对应key的消息内容
    4、消息的使用
       I18nUtils.getMessage(ErrorCode.Common.sessionTimeout)
    5、消息在新应用中如何使用
         public interface ErrorCode extends ErrorCode{
              enum Plm implements IMessage{
                  userNotFound(1203); 
                  private int code; 
                  private String category; 
                  Plm(int code){
                      this.code = code;
                      this.category = this.getClass().getSimpleName();
                  } 
                  public int getCode(){
                      return code;
                  } 
                  public String getCategory(){return category;}
              };
         }
        在web/resources/i18n/messages_zh_CN.properties下定义对应key的消息内容
     6、开发人员，只需要注意定义ErrorCode枚举项，定义message.properties消息内容，即可通过I18nUtils使用

### 1.3.10. websocket用法

    - 1、描述

            框架采用SockJS + STOMP协议 + WebSocket + Redis实现分布式消息推送服务。

    - 2、前端

            var socket = new SockJS('/inno-bpm-web/webSocketServer');
            stompClient = Stomp.over(socket);            
            stompClient.connect({}, function(frame) {
                stompClient.subscribe('/topic/' + {topic}, function(message){
                });
                stompClient.subscribe('/user/queue/' + {topic}, function(message){
                });
            });
            
            SocketJS建立webSocket连接
            subscribe订阅服务端消息推送的通道，包括主题消息通道和个人消息队列，可根据不同的业务订阅不同的通道
            
            主题通道：/topic/，其中{topic}是与业务相关的通道
                负责接收服务端向用户广播的信息，只要订阅主题通道，就可以收到由服务端向这条通到推送的信息
            个人队列：/user/queue/，其中{topic}是与业务相关通道
                负责接收服务端向指定用户推送的消息

    - 3、后端

            RedisMessage封装了服务端推送的信息
                private Object object;              //要推送的对象
                private List<String> userIds;       //要推送的用户集合
                private String channel;             //要推送的通道，与业务相关
                private boolean broadcast;          //是否广播
            提供IRedisPubSubService，可以向Redis通道发布消息，然后由redis的Pub/Sub机制向webSocket通道推送
                redisPubSubService.publish(new RedisMessage(sysNotification, toUsers, "inno.bpm.sysNotification", false));
                业务场景：流程消息发送。
                说明：  在流程执行过程中，需要向指定用户发送消息。
                        redisPubSubService向inno.bpm.sysNotification通道，将sysNotification对象发送个toUsers集合的用户。
                        前端订阅的地址为/user/queue/bpm.sysNotification
                
                redisPubSubService.publish(new RedisMessage(sysAnnoucementDTO, null, "inno.bpm.sysAnnouncement", true));
                业务场景：公告推送
                说明：  管理员新增一条公告，广播到所有用户。
                        redisPubSubService向inno.bpm.sysAnnouncement通道广播sysAnnoucementDTO对象
                        前端订阅的地址为/topic/bpm.sysAnnoucement

### 1.3.11. 实体的监听与逻辑删除
    - 1、实现ICreateListenable接口，可实现JPA新增该实体时，自动填充creator、createdTime两个字段值
    - 2、实现IModifyListenable接口，可实现JPA修改该实体时，自动填充creator、createdTime两个字段值
    - 3、实现IDeleteListenable接口，调用JPA删除实体时，将进行逻辑删除
### 1.3.12. email的用法 ###
    - 1、application.properties配置,由spring-boot的MailSenderAutoConfiguration自动加载
            spring.mail.host=localhost
            spring.mail.port=587
            spring.mail.username=
            spring.mail.password=
      2、使用说明，参照bpm工程的SysMailSendServiceTest
         @Autowired
         ISysMailSendService sysMailSendService;
         //保存待发邮件
         SysMailSend item = new SysMailSend();
         item.setContent("邮件内容");
         item.setTitle("邮件主题");
         item.setToUsers("xx.xx@e-vada.com");
         sysMailSendService.create(item);
         //默认任务调度会轮循发邮件，手动发送邮件采用此方法
         //sysMailSendService.sendEmail();
### 1.3.13. session-timeout配置 ###
      使用说明：增加读取spring-boot的server.session-timeout配置。session超时只要配置application.properties的server.session-timeout即可，单位：秒(S)
      server.session-timeout= 3600
      具体实现：RedisSessionConfig.java
      
      @Value("${server.session-timeout}")
      	private int maxInactiveIntervalInSeconds;
      
      	@Primary
      	@Bean
      	public RedisOperationsSessionRepository sessionRepository(RedisTemplate<String, ExpiringSession> sessionRedisTemplate) {
      		RedisOperationsSessionRepository sessionRepository = new RedisOperationsSessionRepository(sessionRedisTemplate);
      		sessionRepository.setDefaultMaxInactiveInterval(maxInactiveIntervalInSeconds);
      		return sessionRepository;
      	}
      	
## 1.4 Message  
  
###1.4.1 订阅和发布  
RedisTopic：Redis主题订阅/发布工具类，示例代码如下：
 
    @Autowired
    private RedisTopic redisTopic;
    
    // 消息主题枚举
    public enum RedisTopicEnum {
        META_MODEL_CACHE_REFRESH(new 	PatternTopic("inno.de.metamodel.refresh"));

        private PatternTopic patternTopic;

        RedisTopicEnum(PatternTopic patternTopic) {
            this.patternTopic = patternTopic;
        }

        public PatternTopic getTopic() {
            return patternTopic;
        }
    }

    public void doMyBussinessLogic() {

        // 订阅消息主题
        redisTopic.subscribe(new RedisTopicMessageListener<Boolean>() {
            @Override
            public void onMessage(Boolean isMetaModelCacheRefreshed) {
                if (isMetaModelCacheRefreshed) {
                    // 清除缓存
                }
            }
        }, RedisTopicEnum.META_MODEL_CACHE_REFRESH.getTopic());

        // 向消息主题发布消息
        redisTopic.publish(RedisTopicEnum.META_MODEL_CACHE_REFRESH.getTopic(), true);
    }    


## 1.5 注意事项    
### 1.5.1 持久层框架混用  
　　Jpa和Mybatis缓存清除，当同一个大的业务中先使用jpa进行数据增删改时，后续若要使用mybatis及时查看增删改的结果，就需要对两个框架同时flush，封装`CacheCleaner`有以下好处：  
 　　1） *省得在Mybatis的Mapper中配置:flushCache="true" useCache="false"*    
 　　2） *省得一直调用repository.saveAndFlush, repository.flush()*

 　　用法:  
 　　1） 注入service层:    

      @Autowired  
      private CacheCleaner cacheCleaner;  
      
 　　2） 在jpa保存或修改后, 需要执行mybatis复杂查询的前面调用:  

	  cacheCleaner.flush()
	  
## 自动更新所有pom的版本号	  
    org.codehaus.mojo:versions-maven-plugin          
###[更新日志](http://112.124.34.33:10080/inno-backend/inno-framework/tree/master/wikis/Changelog.md)

### 1.6.0 分布式shiro 和 session
    目前还未完成支持分布式redis缓存。在提供给app做api调用的时候不启用shiro，所以目前不生效，不会有问题。后续需要改进

### 关于sysUer和tUser
    1） 目前红三毛系统里面将系统用户（估计是后台用户），和app用户（客户）分为两个不同的表
    2） 系统做后台管理是有加入shiro权限控制
    3）app用户没有加入权限控制，权限控制模块需要单独去写
### maven 没有oracle包的解决方法
    1） cd 到 lib下 执行：mvn install:install-file -Dfile=ojdbc7-1.0.jar -DgroupId=com.oracle.ojdbc7 -DartifactId=ojdbc7 -Dversion=1.0 -Dpackaging=jar
    2） 直接复制包到maven仓库下
