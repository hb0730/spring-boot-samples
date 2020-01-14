package cn.hb0730.spring.boot.event;

import org.springframework.context.ApplicationEvent;

/**
 * <p>
 * 自定义事件
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public class DemoEvent extends ApplicationEvent {
    private String message;

    public DemoEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
