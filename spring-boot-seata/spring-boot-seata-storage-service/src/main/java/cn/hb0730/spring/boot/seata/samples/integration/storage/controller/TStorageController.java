package cn.hb0730.spring.boot.seata.samples.integration.storage.controller;

import cn.hb0730.spring.boot.seata.samples.integration.common.dto.CommodityDTO;
import cn.hb0730.spring.boot.seata.samples.integration.common.response.ObjectResponse;
import cn.hb0730.spring.boot.seata.samples.integration.storage.service.ITStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 * * @author bing_huang
 */
@RestController
@RequestMapping("/storage")
@Slf4j
public class TStorageController {


    @Autowired
    private ITStorageService storageService;

    /**
     * 扣减库存
     */
    @PostMapping("dec_storage")
    ObjectResponse decreaseStorage(@RequestBody CommodityDTO commodityDTO) {
        log.info("请求库存微服务：{}", commodityDTO.toString());
        return storageService.decreaseStorage(commodityDTO);
    }
}

