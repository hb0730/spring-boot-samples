package com.hb0730.spring.boot.seata.samples.integration.common.dubbo;

import com.hb0730.spring.boot.seata.samples.integration.common.dto.OrderDTO;
import com.hb0730.spring.boot.seata.samples.integration.common.response.ObjectResponse;

/**
 * <p>
 *      订单服务接口
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public interface OrderDubboService {

    /**
     * 创建订单
     */
    ObjectResponse<OrderDTO> createOrder(OrderDTO orderDTO);
}
