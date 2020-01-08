package cn.hb0730.spring.boot.close.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/")
public class TestController {
    private Logger logger= LoggerFactory.getLogger(TestController.class);
    @Resource
    private ConfigurableApplicationContext configurableApplicationContext;
    @Resource
    private ApplicationContext applicationContext;
    @RequestMapping("/showdown")
    public void showdown() {
        configurableApplicationContext.close();
    }

    @RequestMapping("/start")
    public void start(){
        configurableApplicationContext.start();
    }

    @RequestMapping("/shop")
    public void stop(){
        configurableApplicationContext.stop();
    }
    @RequestMapping("/exceptionDown")
    public void exceptionDown(){
        logger.error("启动失败，初始化失败");
        SpringApplication.exit(applicationContext,() -> 42);
    }

}
