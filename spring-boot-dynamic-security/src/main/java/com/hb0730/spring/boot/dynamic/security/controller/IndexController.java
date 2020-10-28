package com.hb0730.spring.boot.dynamic.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bing_huang
 */
@RestController
public class IndexController {

    @GetMapping("/home/index")
    public Authentication index() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
