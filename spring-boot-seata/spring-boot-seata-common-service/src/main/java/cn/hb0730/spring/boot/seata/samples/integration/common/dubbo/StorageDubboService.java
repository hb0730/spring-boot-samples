package cn.hb0730.spring.boot.seata.samples.integration.common.dubbo;

import cn.hb0730.spring.boot.seata.samples.integration.common.dto.CommodityDTO;
import cn.hb0730.spring.boot.seata.samples.integration.common.response.ObjectResponse;

/**
 * <p>
 * 库存服务
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public interface StorageDubboService {

    /**
     * 扣减库存
     */
    ObjectResponse decreaseStorage(CommodityDTO commodityDTO);
}
