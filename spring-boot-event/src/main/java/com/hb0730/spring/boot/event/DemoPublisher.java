package com.hb0730.spring.boot.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 消息发布者
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Component
public class DemoPublisher {

    private final ApplicationContext applicationContext;

    @Autowired
    public DemoPublisher(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void publish(String message) {
        applicationContext.publishEvent(new DemoEvent(this, message));
    }

}
