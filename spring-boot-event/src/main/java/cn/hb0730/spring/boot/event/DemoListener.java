package cn.hb0730.spring.boot.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 事件监听器
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Component
public class DemoListener implements ApplicationListener<DemoEvent> {

    @Override
    public void onApplicationEvent(DemoEvent demoEvent) {
        String msg = demoEvent.getMessage();
        System.out.println("(bean-demoListener implements ApplicationListener<DemoEvent>)接收到了bean-demoPublisher发布的消息:" + msg);
    }
}
