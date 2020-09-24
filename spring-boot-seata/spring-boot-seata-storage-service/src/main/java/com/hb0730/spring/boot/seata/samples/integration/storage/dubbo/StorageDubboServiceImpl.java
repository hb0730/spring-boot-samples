package com.hb0730.spring.boot.seata.samples.integration.storage.dubbo;


import com.hb0730.spring.boot.seata.samples.integration.common.dto.CommodityDTO;
import com.hb0730.spring.boot.seata.samples.integration.common.dubbo.StorageDubboService;
import com.hb0730.spring.boot.seata.samples.integration.common.response.ObjectResponse;
import com.hb0730.spring.boot.seata.samples.integration.storage.service.ITStorageService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: bing_huang
 * @Description
 */
@Service(version = "1.0.0", protocol = "${dubbo.protocol.id}",
        application = "${dubbo.application.id}", registry = "${dubbo.registry.id}",
        timeout = 3000)
@Slf4j
public class StorageDubboServiceImpl implements StorageDubboService {

    @Autowired
    private ITStorageService storageService;

    @Override
    public ObjectResponse decreaseStorage(CommodityDTO commodityDTO) {
        log.info("全局事务id ：" + RootContext.getXID());
        return storageService.decreaseStorage(commodityDTO);
    }
}
