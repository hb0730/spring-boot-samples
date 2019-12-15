# spring-boot-samples
spring boot 案例
## 方法级注解 ParamNotNull 用于判断参数不允许为空
在现有的除了Spring中`@Validated`以及lombok的`NonNull`可以做到参数为空抛异常之外，其余的判断为空几乎不会抛出异常，故写出该案例
本案例采用Spring AOP中`@Before`进行前置拦截以及自定义注解`ParamsNotNull`进行实现
# spring-boot-ddynamictask
spring 定时任务的动态化配置
