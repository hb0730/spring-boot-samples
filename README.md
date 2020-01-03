# spring-boot-samples
spring boot 案例
## 方法级注解 ParamNotNull 用于判断参数不允许为空
在现有的除了Spring中`@Validated`以及lombok的`NonNull`可以做到参数为空抛异常之外，其余的判断为空几乎不会抛出异常，故写出该案例
本案例采用Spring AOP中`@Before`进行前置拦截以及自定义注解`ParamsNotNull`进行实现
# spring-boot-dynamictask
spring 定时任务的动态化配置
# spring-boot-rocketmq
 ## apache-rocketmq-spring-boot
  spring-boot 集成rocketMQ <http://rocketmq.apache.org/docs/filter-by-sql92-example/>
 ## alibaba-rocketmq-spring-boot
 spring-boot 集成 aliMQ <https://github.com/AliwareMQ/mq-demo>
# spring-boot-rabbitmq
 ## rabbitmq-spring-boot-rabbitmq
  spring-boot 集成rabbitmq <https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/reference/html/spring-boot-features.html#boot-features-amqp>
 ## alibaba-rabbitmq-spring-boot
 spring-boot 集成 aliAMQP <https://github.com/AliwareMQ/amqp-demos/tree/master/amqp-springboot-demo/SprintBootDemo?spm=a2c4g.11186623.2.15.221a1eb3YRP2Ci>
# spring-boot-starter-samples
spring boot starter制作案例