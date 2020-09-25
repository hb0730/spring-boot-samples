package com.hb0730.sample.i18n.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.Cookie;
import java.util.Locale;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Slf4j
public class HelloControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void cookieTest() throws Exception {
        Cookie cookie = new Cookie("cookie-lang", "zh-TW");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/hello").cookie(cookie)).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        log.info(resultContent);
    }

    @Test
    public void sessionTest() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session-lang", Locale.forLanguageTag("en-US"));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/hello").session(session)).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        log.info(resultContent);
    }

    @Test
    public void headerTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Language", "zh-TW");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/hello").headers(headers)).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        log.info(resultContent);
    }
}
