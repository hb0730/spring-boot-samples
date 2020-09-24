package com.hb0730.spring.boot.seata.samples.integration.account.service;


import com.hb0730.spring.boot.seata.samples.integration.account.entity.TAccount;
import com.hb0730.spring.boot.seata.samples.integration.common.dto.AccountDTO;
import com.hb0730.spring.boot.seata.samples.integration.common.response.ObjectResponse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 * <p>
 * * @author bing_huang
 */
public interface ITAccountService extends IService<TAccount> {

    /**
     * 扣用户钱
     */
    ObjectResponse decreaseAccount(AccountDTO accountDTO);
}
