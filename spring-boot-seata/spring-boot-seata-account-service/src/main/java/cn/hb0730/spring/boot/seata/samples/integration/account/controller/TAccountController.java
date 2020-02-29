package cn.hb0730.spring.boot.seata.samples.integration.account.controller;

import cn.hb0730.spring.boot.seata.samples.integration.account.service.ITAccountService;
import cn.hb0730.spring.boot.seata.samples.integration.common.dto.AccountDTO;
import cn.hb0730.spring.boot.seata.samples.integration.common.response.ObjectResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 账户扣钱
 * </p>
 * <p>
 * * @author bing_huang
 */
@RestController
@RequestMapping("/account")
@Slf4j
public class TAccountController {

    @Autowired
    private ITAccountService accountService;

    @PostMapping("/dec_account")
    ObjectResponse decreaseAccount(@RequestBody AccountDTO accountDTO) {
        log.info("请求账户微服务：{}", accountDTO.toString());
        return accountService.decreaseAccount(accountDTO);
    }
}

