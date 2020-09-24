package com.hb0730.spring.boot.seata.samples.integration.common.dubbo;

import com.hb0730.spring.boot.seata.samples.integration.common.dto.AccountDTO;
import com.hb0730.spring.boot.seata.samples.integration.common.response.ObjectResponse;

/**
 * <p>
 * 账户服务接口
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public interface AccountDubboService {

    /**
     * 从账户扣钱
     */
    ObjectResponse decreaseAccount(AccountDTO accountDTO);
}
