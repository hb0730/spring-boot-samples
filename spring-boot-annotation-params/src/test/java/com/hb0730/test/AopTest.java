package com.hb0730.test;

import com.hb0730.annotation.ParamsNotNull;
import org.junit.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * <p></p>
 *
 * @author bing_huang
 * @version V1.0
 * All rights Reserved, Designed By www.hohofast.com &#13;
 * 注意：本内容仅限于上海极吼吼信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 &#13;
 * @date 2019/9/17 11:10
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class AopTest {


    @Test
    public void test(){
        params("");
    }
    @ParamsNotNull(name = {"id"},message = "id不为空")
    public  void params(String id){

    }
}
