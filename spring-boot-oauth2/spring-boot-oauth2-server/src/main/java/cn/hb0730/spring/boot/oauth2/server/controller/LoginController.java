package cn.hb0730.spring.boot.oauth2.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Controller
public class LoginController {

    @GetMapping(value = {"/login"})
    public String getLoginPage(){
        return "login";
    }

    @GetMapping(value = "/index")
    public String index(){
        return "index";
    }

    @GetMapping(value = "/403")
    public String Error403(){
        return "403";
    }
}
