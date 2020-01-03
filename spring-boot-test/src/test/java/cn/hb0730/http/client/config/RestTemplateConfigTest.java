package cn.hb0730.http.client.config;

import cn.hb0730.test.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles
public class RestTemplateConfigTest {

    @Resource
    private RestTemplate httpClientRestTemplate;

    @Test
    public void test(){
        ResponseEntity<String> forEntity = httpClientRestTemplate.getForEntity("http://localhost:8089//system/parameterInfoBusiness/get_all_para", String.class);

    }
}
