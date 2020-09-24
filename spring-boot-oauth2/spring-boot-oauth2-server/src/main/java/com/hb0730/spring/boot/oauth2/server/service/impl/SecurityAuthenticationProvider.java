package com.hb0730.spring.boot.oauth2.server.service.impl;

import com.hb0730.spring.boot.oauth2.server.mapper.UserMapper;
import com.hb0730.spring.boot.oauth2.server.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collection;

/**
 * <p>
 *     自定义身份验证
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 * @see  <a href="https://docs.spring.io/spring-security/site/docs/5.3.0.M1/reference/htmlsingle/#jc-authentication-authenticationprovider">servlet-authentication</a>
 */
@Service
public class SecurityAuthenticationProvider implements AuthenticationProvider {
    private Logger logger = LoggerFactory.getLogger(SecurityAuthenticationProvider.class);
    private UserDetailsServiceImpl userDetailsService;

    private PasswordEncoder passwordEncoder;

    private UserMapper userService;
    HttpSession session;

    public SecurityAuthenticationProvider(UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder,
                                          UserMapper userService, HttpSession session) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.session = session;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();// 这个获取表单输入中的用户名
        String password = (String) authentication.getCredentials();// 这个是表单中输入的密码
        String encPwd = passwordEncoder.encode(password);
        System.out.println("encPwd = [" + encPwd + "]");
        /** 判断用户是否存在 */
        User user = (User)userDetailsService.loadUserByUsername(userName); // 这里调用我们的自己写的获取用户的方法
        if (user == null) {
            throw new BadCredentialsException("用户不存在");
        }

        /** 判断密码是否正确 */
//        if (!passwordEncoder.matches(password,user.getPassword())) {
//            throw new BadCredentialsException("密码不正确");
//        }
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
//        session.setAttribute("user",user);
        logger.info(String.format("sessionId: %s",session.getId()));
        // 保存用户登录的sessionId,管理端强制登出指定用户
        userService.updateUserSessionIdByUserId(user.getUserId(),session.getId());
        return new UsernamePasswordAuthenticationToken(user, password, authorities);// 构建返回的用户登录成功的token
    }


    /**
     * 执行支持判断
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return true;// 返回 true ，表示支持执行
    }
}
