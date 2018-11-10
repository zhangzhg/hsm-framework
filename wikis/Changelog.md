##更新日志

##1.0.0.RELEASE - 2015-06-06

 - 支持`mybatis`分页，引入Mybatis-PageHelper

##1.0.0.RELEASE - 2015-09-08

 - spring升级4.2.1 至json消息转换问题，修改RESTDataHttpMessageConverter.writeInternal增加参数Type type

##1.0.1.RELEASE - 2015-11-24

 - spring-boot升级1.3.0.RELEASE
 - 完善spring.datasource的高级配置
 - UserInfo增加头像属性
 - 修复BaseRelationService分页参数错误问题

##1.0.2.RELEASE - 2015-12-01

 - BeanUtils.convertMap支持对象中的List属性传入JSONArray
 - filterParser转换时,增加MatchType校验
 - 抽取根据principalType反射获取对应Service的方法
 - websocketConfig增加setAllowedOrigins("http://localhost")
 - BaseJpaRepositoryImpl deleteByIds增加@Transactional
 - TreeModel获取实体类型
 - Page参数(page、limit)合法性校验

##1.0.2.RELEASE - 2015-12-16

 - DataSourceConfig配置hibernate.ejb.interceptor.session_scoped支持非线程安全的session

##1.0.2.RELEASE - 2015-12-21
 - 增加上传文件大小统一配置 MultipartFileConfig
 - jpa的delete对象方法 标识为废弃方法，尽量少用
 - 全局异常增加，Jpa的resourceNotFound异常EmptyResultDataAccessException处理
 - 修复获取国际化信息失败后,原始异常码被覆盖丢失的问题
 
##1.0.2.RELEASE - 2015-12-23
 - BeanUtils 日期转换 支持字符串类型的时间戳
 - websocketconfig修改setAllowedOrigins允许跨域请求

##1.0.2.RELEASE - 2015-12-24
 - Quartz动态创建任务调整
 - MultipartException 上传附件大小限制异常

##1.0.2.RELEASE - 2016-01-02
 - JPA LIKE模糊查询 忽略大小写

##1.0.2.RELEASE - 2016-01-12
 - HibernateCRUD监听

##1.0.2.RELEASE - 2016-01-13
 - deleteByIds 批量物理删除修改

##1.0.2.RELEASE - 2016-01-16
 - 消息模块字典调整 
 
##1.0.2.RELEASE - 2016-01-18
 - 支持BaseJpaRepositoryImpl delete(Iterator<T> entities)参数为空

##1.0.2.RELEASE - 2016-01-19
 - 消息Channel写成枚举常量

##1.0.2.RELEASE - 2016-01-20
 - 增加根据特定条件集合删除对象 deleteInBatch(List<T> objects)

##1.0.2.RELEASE - 2016-01-26
 - websocket配置增加httpSession握手拦截器  
  
##1.0.2.RELEASE - 2016-03-19
 - DataSourceConfig中的SqlSessionFactory的创建增加日志打印记录，防止spring容器抑制bean创建异常，导致只输出warn的有限的异常信息，不方便开发调试。  
  
##1.0.2.RELEASE - 2016-03-24
 - 增加TicketHttpSessionStrategy策略 
 
## 1.0.3.RELEASE - 2016-04-15
 - 全局异常返回errorCode取值错误，并升级版本     
   return new ErrorMessage(this.appNo + ex.getType() + ex.getErrorCode(), ex.getMsg());
    改为 return new ErrorMessage(this.appNo + ex.getType() + ex.getErrorCode().getCode(), ex.getMsg());
    如：000-B-sessionTimeout应返回000-B-1103   

## 1.0.4.RELEASE - 2016-04-17
 - 增加缓存配置、缓存对象反序列化  

## 1.0.5.RC1 - 2016-04-22
 - 创建人、创建时间、最后修改人、最后修改时间从HibernateEjbIntercepto调整到EntityEventListener  
 - 增加jpa和mybatis缓存清除组件CacheCleaner 

## 1.0.5.RC2 - 2016-05-02
 - 日志模块调整到core上  
  
## 1.0.5.RC3 - 2016-05-06
 - RedisMessageListener 重构名称为 RedisWebSocketMessageListener  
 - 增加RedisTopic消息主题工具类，支持消息主题订阅和发布
## 1.0.5.RC5 - 2016-05-19
 - redis增加默认缓存一天
 - 增加DESUtils工具类
## 1.0.5.RC6 - 2016-05-20
 - 增加内存缓存
 - des加密key至少8位改为先base64加密作为KEY
## 1.0.5.RC7 - 2016-05-21
 - 增加内存缓存
 - des加密key至少8位改为先md5加密作为KEY
## 1.0.7.RC1 - 2016-05-30
 - 将项目配置剥离到项目外，数据源密码加密
## 1.0.7.RC2 - 2016-06-8
 - sessionUtils和EventEntityListener增加默认的systeUserId
## 1.0.7.RELEASE - 2016-08-02
 - 增加hibernate batch-size
 - 优化EventEntityListener获取systeUserId
## 1.0.8.RC2 - 2016-09-05
 - 增加inno-qrcode二维码组件模块
## 1.0.8.RC3 - 2016-09-05
 - inno-qrcode二维码组件增加生成到OutputStream相关接口
## 1.0.8.RC4 - 2016-09-13
 - 下载文件工具类修改文件不存在为抛出异常的方式, 客户端的jqueryFileDownload才能识别到onfail
## 1.0.8.RC5 - 2016-09-19
 - 更新inno-framework的jdk版本到1.8
 - 增加inno-eventbus模块