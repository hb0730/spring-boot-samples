package com.hb0730.spring.boot.seata.samples.integration.account.mapper;

import com.hb0730.spring.boot.seata.samples.integration.account.entity.TAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 * <p>
 * * @author bing_huang
 */
public interface TAccountMapper extends BaseMapper<TAccount> {

    /**
     * 减少账户余额
     *
     * @param userId
     * @param amount
     * @return
     */
    int decreaseAccount(@Param("userId") String userId, @Param("amount") Double amount);
}
