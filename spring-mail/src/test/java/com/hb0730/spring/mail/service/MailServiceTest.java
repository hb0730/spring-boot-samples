package com.hb0730.spring.mail.service;

import com.hb0730.spring.mail.properties.EmailProperties;
import com.hb0730.spring.mail.service.impl.MailServiceImpl;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles
public class MailServiceTest extends TestCase {
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    String username = "";
    String password = "";
    String host = "smtp.qq.com";
    int port = 465;
    String fromName = "hb0730";
    String protocol = "smtp";

    @Test
    public void testTestConnection() {
        EmailProperties properties = new EmailProperties();
        properties.setUsername(username);
        properties.setPassword(password);
        properties.setHost(host);
        properties.setSslPort(port);
        properties.setFromName(fromName);
        properties.setProtocol(protocol);
        MailService service = new MailServiceImpl(properties, freeMarkerConfigurer);
        service.testConnection();
    }

    @Test
    public void testSendTextMail() {
        EmailProperties properties = new EmailProperties();
        properties.setUsername(username);
        properties.setPassword(password);
        properties.setHost(host);
        properties.setSslPort(port);
        properties.setFromName(fromName);
        properties.setProtocol(protocol);
        MailService service = new MailServiceImpl(properties, freeMarkerConfigurer);
        service.sendTextMail(username, "测试", "hhh");
    }

    @Test
    public void testSendTemplateMail() {
        EmailProperties properties = new EmailProperties();
        properties.setUsername(username);
        properties.setPassword(password);
        properties.setHost(host);
        properties.setSslPort(port);
        properties.setFromName(fromName);
        properties.setProtocol(protocol);
        MailService service = new MailServiceImpl(properties, freeMarkerConfigurer);
        Map<String, Object> maps = new HashMap<>();
        maps.put("test", "hhh");
        service.sendTemplateMail(username, "测试", maps, "mail_test.ftl");
    }

    @Test
    public void testSendAttachMail(){
        EmailProperties properties = new EmailProperties();
        properties.setUsername(username);
        properties.setPassword(password);
        properties.setHost(host);
        properties.setSslPort(port);
        properties.setFromName(fromName);
        properties.setProtocol(protocol);
        MailService service = new MailServiceImpl(properties, freeMarkerConfigurer);
        Map<String, Object> maps = new HashMap<>();
        maps.put("test", "hhh");
        service.sendAttachMail(username,"测试",maps,"mail_test.ftl","D:\\haha3.txt");
    }
}
