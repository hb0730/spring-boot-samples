package com.hb0730.spring.boot.seata.samples.integration.order.mapper;

import com.hb0730.spring.boot.seata.samples.integration.order.entity.TOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 * <p>
 * * @author bing_huang
 *
 * @since 2019-09-04
 */
public interface TOrderMapper extends BaseMapper<TOrder> {

    /**
     * 创建订单
     *
     * @Param: order 订单信息
     * @Return:
     */
    void createOrder(@Param("order") TOrder order);
}
