package cn.hb0730.test.params.anntation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * <p></p>
 *
 * @author bing_huang
 * @version V1.0
 * @date 2019/9/18 8:53
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles
public class ParamsAnntationTest {
    @Autowired
    private MockMvc mvc;
    @Test
    public void test() throws Exception {
        String url="/api/test";
        MvcResult mvcResult  = mvc.perform(MockMvcRequestBuilders.get(url)
                .param("id","")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        String contentAsByteArray = response.getContentAsString();
        System.out.println(status);
        System.out.println(contentAsByteArray);
    }
}
