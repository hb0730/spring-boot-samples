package cn.hb0730.spring.boot.seata.samples.integration.storage.mapper;

import cn.hb0730.spring.boot.seata.samples.integration.storage.entity.TStorage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public interface TStorageMapper extends BaseMapper<TStorage> {

    /**
     * 扣减商品库存
     *
     * @Param: commodityCode 商品code  count扣减数量
     * @Return:
     */
    int decreaseStorage(@Param("commodityCode") String commodityCode, @Param("count") Integer count);
}

