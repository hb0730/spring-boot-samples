package com.hb0730.alibaba.spring.boot.rabbitmq.test;

import com.hb0730.alibaba.spring.boot.rabbitmq.callback.SenderWithCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@RestController
@RequestMapping(value = "/test", produces = "application/json;charset=UTF-8")
public class TestSenderController {

    @Resource
    private SenderWithCallback sendWithCallback;

    @Resource
    private Sender sender;

    @RequestMapping(value = "/send")
    public String sendMessage(@RequestParam(name = "withCallback", required = false) boolean withCallback) {
        if (withCallback) {
            sendWithCallback.send();
        } else {
            sender.send();
        }

        return "Success";
    }
}
