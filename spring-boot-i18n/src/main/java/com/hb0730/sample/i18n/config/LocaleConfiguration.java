package com.hb0730.sample.i18n.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * 国际化配置
 *
 * @author bing_huang
 */
@Configuration
public class LocaleConfiguration implements WebMvcConfigurer {
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
