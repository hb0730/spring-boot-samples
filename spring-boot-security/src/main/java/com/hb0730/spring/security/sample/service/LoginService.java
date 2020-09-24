package com.hb0730.spring.security.sample.service;

import com.hb0730.spring.security.sample.model.LoginSuccess;
import com.hb0730.spring.security.sample.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Component
public class LoginService {
    @Autowired
    private TokenServiceImpl tokenService;

    @Resource
    private AuthenticationManager authenticationManager;


    /**
     * <p>
     * 用户登录
     * </p>
     *
     * @param username 用户账号
     * @param password 用户密码
     * @return token令牌
     */
    public LoginSuccess login(String username, String password) {
        // 删除缓存

        // 用户验证
        Authentication authentication = null;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {


        }
        LoginUser user = (LoginUser) authentication.getPrincipal();
        // 生成token
        String accessToken = tokenService.createAccessToken(user);
        LoginSuccess success = new LoginSuccess();
        success.setAccessToken(accessToken);
        success.setLoginUser(user);
        return success;
    }
}
