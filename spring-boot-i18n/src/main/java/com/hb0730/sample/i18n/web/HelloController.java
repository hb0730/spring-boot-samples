package com.hb0730.sample.i18n.web;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author bing_huang
 */
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
