package alibaba.spring.boot.batch;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.batch.BatchMessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Component
public class BatchDemoMessageListener implements BatchMessageListener {

    @Override
    public Action consume(List<Message> messages, ConsumeContext consumeContext) {
        System.out.println("Receive: " + messages.size() + " messages");
        for (Message message : messages) {
            System.out.println(message);
        }
        try {
            //do something..
            return Action.CommitMessage;
        } catch (Exception e) {
            //消费失败
            return Action.ReconsumeLater;
        }
    }
}
