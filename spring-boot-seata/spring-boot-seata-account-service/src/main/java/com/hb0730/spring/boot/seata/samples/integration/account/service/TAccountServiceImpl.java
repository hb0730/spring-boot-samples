package com.hb0730.spring.boot.seata.samples.integration.account.service;

import com.hb0730.spring.boot.seata.samples.integration.account.entity.TAccount;
import com.hb0730.spring.boot.seata.samples.integration.account.mapper.TAccountMapper;
import com.hb0730.spring.boot.seata.samples.integration.common.dto.AccountDTO;
import com.hb0730.spring.boot.seata.samples.integration.common.enums.RspStatusEnum;
import com.hb0730.spring.boot.seata.samples.integration.common.response.ObjectResponse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 * <p>
 * * @author bing_huang
 */
@Service
public class TAccountServiceImpl extends ServiceImpl<TAccountMapper, TAccount> implements ITAccountService {

    @Override
    public ObjectResponse decreaseAccount(AccountDTO accountDTO) {
        int account = baseMapper.decreaseAccount(accountDTO.getUserId(), accountDTO.getAmount().doubleValue());
        ObjectResponse<Object> response = new ObjectResponse<>();
        if (account > 0) {
            response.setStatus(RspStatusEnum.SUCCESS.getCode());
            response.setMessage(RspStatusEnum.SUCCESS.getMessage());
            return response;
        }

        response.setStatus(RspStatusEnum.FAIL.getCode());
        response.setMessage(RspStatusEnum.FAIL.getMessage());
        return response;
    }
}
