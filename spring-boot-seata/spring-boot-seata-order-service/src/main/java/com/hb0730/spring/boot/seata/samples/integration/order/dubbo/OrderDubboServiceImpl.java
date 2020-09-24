package com.hb0730.spring.boot.seata.samples.integration.order.dubbo;

import com.hb0730.spring.boot.seata.samples.integration.common.dto.OrderDTO;
import com.hb0730.spring.boot.seata.samples.integration.common.dubbo.OrderDubboService;
import com.hb0730.spring.boot.seata.samples.integration.common.response.ObjectResponse;
import com.hb0730.spring.boot.seata.samples.integration.order.service.ITOrderService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: bing_huang
 */
@Service(version = "1.0.0", protocol = "${dubbo.protocol.id}",
        application = "${dubbo.application.id}", registry = "${dubbo.registry.id}",
        timeout = 3000)
@Slf4j
public class OrderDubboServiceImpl implements OrderDubboService {

    @Autowired
    private ITOrderService orderService;

    @Override
    public ObjectResponse<OrderDTO> createOrder(OrderDTO orderDTO) {
        log.info("全局事务id ：" + RootContext.getXID());
        return orderService.createOrder(orderDTO);
    }
}
