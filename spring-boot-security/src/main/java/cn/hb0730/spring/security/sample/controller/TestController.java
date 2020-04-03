package cn.hb0730.spring.security.sample.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test1")
    @PreAuthorize("hasAnyAuthority('select:query')")
    public String test1() {
        return "访问成功";
    }

    @GetMapping("/test2")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String test2() {
        return "访问成功";
    }

    @GetMapping("/test3")
    @PreAuthorize("hasAnyAuthority('select:update','ROLE_TEST')")
    public String test3() {

        return "访问成功";
    }

    @GetMapping("/test4")
    public String test4() {
        return "访问成功";
    }
}
