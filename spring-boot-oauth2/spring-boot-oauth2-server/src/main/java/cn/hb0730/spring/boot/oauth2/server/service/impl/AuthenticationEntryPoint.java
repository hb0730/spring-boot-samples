package cn.hb0730.spring.boot.oauth2.server.service.impl;

import cn.hb0730.spring.boot.oauth2.server.model.CodeMessage;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *     认证入口点
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 * @see <a href="https://docs.spring.io/spring-security/site/docs/5.3.0.M1/reference/htmlsingle/#tech-intro-auth-entry-point">AuthenticationEntryPoint</a>
 */
@Service
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {
    /**
     * Performs the redirect (or forward) to the login form URL.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // redirect to login page. Use https if forceHttps true

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONObject.toJSONString(CodeMessage.NoLogin));// 返回 JSON 信息
        response.flushBuffer();
    }
}
