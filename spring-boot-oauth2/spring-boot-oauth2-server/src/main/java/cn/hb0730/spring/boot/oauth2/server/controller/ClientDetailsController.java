package cn.hb0730.spring.boot.oauth2.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *     动态client
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@RestController
@RequestMapping("client")
public class ClientDetailsController {
    @Autowired
    private JdbcClientDetailsService clientRegistrationService;

    @GetMapping("/getAll")
    public List getAll() {
        return clientRegistrationService.listClientDetails();
    }
}
