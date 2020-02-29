package cn.hb0730.spring.boot.seata.samples.integration.call.service;


import cn.hb0730.spring.boot.seata.samples.integration.common.dto.BusinessDTO;
import cn.hb0730.spring.boot.seata.samples.integration.common.response.ObjectResponse;

/**
 * @Author: bing_huang
 */
public interface BusinessService {

    /**
     * 出处理业务服务
     *
     * @param businessDTO
     * @return
     */
    ObjectResponse handleBusiness(BusinessDTO businessDTO);


    /**
     * 出处理业务服务，出现异常回顾
     *
     * @param businessDTO
     * @return
     */
    ObjectResponse handleBusiness2(BusinessDTO businessDTO);
}
