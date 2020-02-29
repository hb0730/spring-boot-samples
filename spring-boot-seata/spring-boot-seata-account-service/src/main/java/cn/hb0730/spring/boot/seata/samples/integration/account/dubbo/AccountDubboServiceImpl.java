package cn.hb0730.spring.boot.seata.samples.integration.account.dubbo;

import cn.hb0730.spring.boot.seata.samples.integration.account.service.ITAccountService;
import cn.hb0730.spring.boot.seata.samples.integration.common.dto.AccountDTO;
import cn.hb0730.spring.boot.seata.samples.integration.common.dubbo.AccountDubboService;
import cn.hb0730.spring.boot.seata.samples.integration.common.response.ObjectResponse;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: bing_huang
 * @Description Dubbo Api Impl
 * @Date Created in 2019/1/23 14:40
 */
@Service(version = "1.0.0", protocol = "${dubbo.protocol.id}",
        application = "${dubbo.application.id}", registry = "${dubbo.registry.id}",
        timeout = 3000)
@Slf4j
public class AccountDubboServiceImpl implements AccountDubboService {

    @Autowired
    private ITAccountService accountService;

    @Override
    public ObjectResponse decreaseAccount(AccountDTO accountDTO) {
        log.info("全局事务id ：" + RootContext.getXID());
        return accountService.decreaseAccount(accountDTO);
    }
}
