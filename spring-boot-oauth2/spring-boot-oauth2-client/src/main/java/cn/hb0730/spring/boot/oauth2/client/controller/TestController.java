package cn.hb0730.spring.boot.oauth2.client.controller;

import cn.hb0730.spring.boot.oauth2.client.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@RestController
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping(value = "/test")
    public String test(Principal principal){
        logger.info("hello {}", principal.getName());
        return "test: " + principal.getName();
    }

    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('FIND_CURRENTUSER')")
    public String listPage(String name){
        if (name == null) {
            throw new BusinessException("异常捕获测试");
        }
        return "list";
    }

}