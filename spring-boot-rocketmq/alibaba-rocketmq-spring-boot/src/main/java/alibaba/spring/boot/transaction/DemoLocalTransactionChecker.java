package alibaba.spring.boot.transaction;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionChecker;
import com.aliyun.openservices.ons.api.transaction.TransactionStatus;
import org.springframework.stereotype.Component;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Component
public class DemoLocalTransactionChecker implements LocalTransactionChecker {
    @Override
    public TransactionStatus check(Message msg) {
        System.out.println("开始回查本地事务状态");
        return TransactionStatus.CommitTransaction; //根据本地事务状态检查结果返回不同的TransactionStatus
    }
}