package cn.hb0730.spring.boot.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * <p>
 *     事件监听器
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Component
public class DemoListener2 {


    @EventListener
    @Async
    public void onApplicationEvent(DemoEvent demoEvent) {
        String msg = demoEvent.getMessage();
        System.out.println("(bean-demoListener  @EventListener)接收到了bean-demoPublisher发布的消息:" + msg);
    }
}
