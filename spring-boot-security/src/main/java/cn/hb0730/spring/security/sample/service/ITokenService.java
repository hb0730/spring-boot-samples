package cn.hb0730.spring.security.sample.service;

import cn.hb0730.spring.security.sample.model.LoginUser;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public interface ITokenService {
    /*
     * 会话超时时间，默认2小时
     */
    long MILLIS_SECOND = 1000;

    /*
     *置换保护时间，默认1小时
     */

    long MILLIS_MINUTE = 60 * MILLIS_SECOND;
    /*
     *旧token延迟过期时间
     */

    Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    /**
     * 创建token
     *
     * @param user 用户信息
     * @return token
     */
    String createAccessToken(LoginUser user);

    /**
     * <p>
     * 删除用户信息
     * </p>
     *
     * @param request 请求
     */
    void delLoginUser(HttpServletRequest request);

    /**
     * <p>
     * 删除accessToken并删除用户信息
     * </P>
     *
     * @param accessToken token令牌
     */
    void deleteAccessToken(String accessToken);

    /**
     * <p>
     * 校验令牌
     * </p>
     *
     * @param user 用户
     */
    void verifyAccessToken(LoginUser user);

    /**
     * 刷新令牌
     *
     * @param user 用户
     */
    void refreshAccessToken(LoginUser user);

    /**
     * <p>
     * 获取在线用户
     * </p>
     *
     * @return 在线用户
     */
    Map<String, LoginUser> getOnline();
}
