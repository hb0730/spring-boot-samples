package cn.hb0730.spring.boot.seata.samples.integration.storage.service;

import cn.hb0730.spring.boot.seata.samples.integration.common.dto.CommodityDTO;
import cn.hb0730.spring.boot.seata.samples.integration.common.response.ObjectResponse;
import cn.hb0730.spring.boot.seata.samples.integration.storage.entity.TStorage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 仓库服务
 * <p>
 * * @author bing_huang
 */
public interface ITStorageService extends IService<TStorage> {

    /**
     * 扣减库存
     */
    ObjectResponse decreaseStorage(CommodityDTO commodityDTO);
}
