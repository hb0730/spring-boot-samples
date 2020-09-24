package com.hb0730.spring.boot.seata.samples.integration.order.service;


import com.hb0730.spring.boot.seata.samples.integration.common.dto.OrderDTO;
import com.hb0730.spring.boot.seata.samples.integration.common.response.ObjectResponse;
import com.hb0730.spring.boot.seata.samples.integration.order.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 创建订单
 * </p>
 * <p>
 * * @author bing_huang
 *
 * @since 2019-09-04
 */
public interface ITOrderService extends IService<TOrder> {

    /**
     * 创建订单
     */
    ObjectResponse<OrderDTO> createOrder(OrderDTO orderDTO);
}
