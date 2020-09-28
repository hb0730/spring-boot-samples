> 源码地址:[spring-boot-i18n](https://github.com/hb0730/spring-boot-samples/tree/master/spring-boot-i18n)

项目采用maven方式构建,其目录结构如下:
<img src="https://github.com/hb0730/spring-boot-samples/blob/master/doc/i18n/i18n-1.png">
# pom
因为我采用的是spring-boot项目
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
# 一、后台国际化
## 1、配置国际化参数
默认解析器：**LocaleResolver** 用于设置当前会话的默认的国际化语言。

默认拦截器：**LocaleChangeInterceptor** 指定切换国际化语言的参数名。例如 **?lang=zh_CN** 表示读取国际化文件**messages_zh_CN.properties**。

```java
/**
 * 国际化配置
 *
 * @author bing_huang
 */
@Configuration
public class LocaleConfiguration implements WebMvcConfigurer {
 /**
     * 默认解析器 其中locale表示默认语言
     */
    @Bean
    public LocaleResolver localeResolver() {
        //session级
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        //默认语言
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }
    /**
     * 拦截lang 参数
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}

```
## 2、添加国际化文件
首先在配置文件 **application.yml** 填写国际化文件的相对路径，表示读取**classpath:/static/i18n/messages_language_country.properties** 。例如：
```yaml
spring:
  messages:
    # 相对路径i18n文件下
    basename: i18n/message
```
然后在 **classpath:/static/i18n** 目录中添加如下国际化文件：

默认文件：**messages.properties**
```properties
##这里填写默认翻译，内容可以留空，但文件必须存在。
hello=你好
```
中文简体：**messages_zh_CN.properties**
```properties
hello=你好
```
中文繁体：**messages_zh_TW.properties**
```properties
hello=你號
```
美式英语：**messages_en_US.properties**
```properties
hello=hello
```
## 3、代码国际化
```java
@RestController
@RequiredArgsConstructor
public class HelloController {
    private final MessageSource source;

    @GetMapping("/hello")
    private Object hello(HttpServletRequest request) {
        //参数1 为message.properties =左边的
        //参数2 为message.properties的占位符
        //参数3 为当前环境下的locale
        return source.getMessage("hello", null, LocaleContextHolder.getLocale());
    }
}
```
## 4.访问
1. Get 请求
 http://localhost:8080/hello?lang=zh-TW
 http://localhost:8080/hello?lang=zh-CN
 http://localhost:8080/hello?lang=en-US
 这里主要是 **LocaleChangeInterceptor** 起到了作用

# 解析器 **LocaleResolver**
1. org.springframework.web.servlet.i18n.CookieLocaleResolver cookie解析
2. org.springframework.web.servlet.i18n.SessionLocaleResolver session解析
3. org.springframework.web.servlet.i18n.FixedLocaleResolver 固定解析
4. org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver header解析

## cookie解析器
1. configuration
```java
/**
     * 默认解析器，cookie
     */
    public CookieLocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        //默认语言
        localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        //解析器cookie名称
        localeResolver.setCookieName("cookie-lang");
        localeResolver.setCookieMaxAge(-1);
        return localeResolver;
    }
```
2. test
 ```java
@Test
    public void cookieTest() throws Exception {
        Cookie cookie = new Cookie("cookie-lang", "zh-TW");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/hello").cookie(cookie)).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        log.info(resultContent);
    }
 ```
## session解析器
1. configuration
```java
@Bean
    public SessionLocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        localeResolver.setLocaleAttributeName("session-lang");
        return localeResolver;
    }
```
2. test
```java
@Test
    public void sessionTest() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session-lang", Locale.forLanguageTag("en-US"));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/hello").session(session)).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        log.info(resultContent);
    }
```
## header解析器
**注意1:** 这里采取了`自定义LocaleResolver: AcceptHeaderResolver`,因为在测试时发现原有的`AcceptHeaderLocaleResolver`并未生效
**注意2:** 每一次在创建`localeResolver()`方法时名称必须是 **localeResolver()** 除非自定义了 **@Bean(localeResolver)** ,原因请看: `WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter#localeResolver()`
**注意3:** 如果采用header方式默认是**不支持时区**,以及参数 **LocaleChangeInterceptor** 的拦截
  
1. 自定义resolver
```java
/**
 * 主要解决request local为空
 *
 * @author bing_huang
 */
public class AcceptHeaderResolver extends AcceptHeaderLocaleResolver {


    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        if (StringUtils.isEmpty(request.getHeader("Accept-Language"))) {
            return Locale.getDefault();
        }
        List<Locale.LanguageRange> list = Locale.LanguageRange.parse(request.getHeader("Accept-Language"));
        Locale locale = Locale.lookup(list, super.getSupportedLocales());
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
```
**请注意:** **super.getSupportedLocales()** 如果没有设置默认为0,
2. configuration
```java
/**
     * 请求头设置Accept-Language,注意方法名
     * <pre>
     *     1.AcceptHeader与其他不兼容，如果使用acceptHeader则其余的无法使用(request Params亦是如此)
     *     2. AcceptHeader的请求则是Accept-Language:zh-CN
     *     3. 为何扩展原有AcceptHeaderLocaleResolver的:因为原有的未生效{@link AcceptHeaderLocaleResolver#findSupportedLocale(HttpServletRequest, List)}下HttpServletRequest#getLocales为空
     *     4. 推荐使用header作为国际化,但是存在时区等问题,具体可以看{@link org.springframework.web.servlet.i18n.CookieLocaleResolver}等源码
     * </pre>
     *
     * @see AcceptHeaderLocaleResolver#resolveLocale
     * @see WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter#localeResolver()
     */
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderResolver localeResolver = new AcceptHeaderResolver();
        localeResolver.setSupportedLocales(Arrays.asList(Locale.SIMPLIFIED_CHINESE, Locale.TRADITIONAL_CHINESE, Locale.US));
        localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return localeResolver;
    }
```
3. test
```java
 @Test
    public void headerTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Language", "zh-TW");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/hello").headers(headers)).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        log.info(resultContent);
    }
```
