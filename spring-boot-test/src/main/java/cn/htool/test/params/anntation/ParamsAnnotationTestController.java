package cn.htool.test.params.anntation;

import cn.htool.annotation.ParamsNotNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p></p>
 *
 * @author bing_huang
 * @version V1.0
 */
@RestController
@RequestMapping("/api")
public class ParamsAnnotationTestController {

    @RequestMapping("/test")
    @ParamsNotNull(name = {"ids"}, message = {"id不为空"})
    public String test(@RequestParam("id") String ids) {

        return "测试成功";
    }
}
