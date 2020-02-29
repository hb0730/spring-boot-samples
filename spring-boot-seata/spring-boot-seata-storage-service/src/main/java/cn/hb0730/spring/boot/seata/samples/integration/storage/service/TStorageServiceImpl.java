package cn.hb0730.spring.boot.seata.samples.integration.storage.service;

import cn.hb0730.spring.boot.seata.samples.integration.common.dto.CommodityDTO;
import cn.hb0730.spring.boot.seata.samples.integration.common.enums.RspStatusEnum;
import cn.hb0730.spring.boot.seata.samples.integration.common.response.ObjectResponse;
import cn.hb0730.spring.boot.seata.samples.integration.storage.entity.TStorage;
import cn.hb0730.spring.boot.seata.samples.integration.storage.mapper.TStorageMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 库存服务实现类
 * </p>
 * <p>
 * * @author bing_huang
 */
@Service
public class TStorageServiceImpl extends ServiceImpl<TStorageMapper, TStorage> implements ITStorageService {

    @Override
    public ObjectResponse decreaseStorage(CommodityDTO commodityDTO) {
        int storage = baseMapper.decreaseStorage(commodityDTO.getCommodityCode(), commodityDTO.getCount());
        ObjectResponse<Object> response = new ObjectResponse<>();
        if (storage > 0) {
            response.setStatus(RspStatusEnum.SUCCESS.getCode());
            response.setMessage(RspStatusEnum.SUCCESS.getMessage());
            return response;
        }

        response.setStatus(RspStatusEnum.FAIL.getCode());
        response.setMessage(RspStatusEnum.FAIL.getMessage());
        return response;
    }
}
