package cn.hb0730.spring.security.sample.service;

import cn.hb0730.spring.security.sample.model.LoginUser;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * token验证处理
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Component
public class TokenServiceImpl implements ITokenService {
    // 存储令牌 token
    private final ConcurrentHashMap<String, String> accessTokenStore = new ConcurrentHashMap<String, String>();
    // 存储token 用户
    private final ConcurrentHashMap<String, LoginUser> authenticationStore = new ConcurrentHashMap<String, LoginUser>();
    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";
    /**
     * 令牌自定义标识
     */
    @Value("${token.header:Authorization}")
    private String header;

    /**
     * 令牌秘钥
     */
    @Value("${token.secret:abcdefghijklmnopqrstuvwxyz}")
    private String secret;

    /**
     * 令牌有效期（默认30分钟）
     */
    @Value("${token.expireTime:30}")
    private int expireTime;

    public User getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            token = accessTokenStore.get(token);
            return authenticationStore.get(getTokenKey(token));
        }
        return null;
    }

    @Override
    public String createAccessToken(LoginUser user) {
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        refreshAccessToken(user);
        String accessToken = extractKey(token);
        accessTokenStore.put(accessToken, token);
        return accessToken;
    }

    @Override
    public void delLoginUser(HttpServletRequest request) {
        String accessToken = getToken(request);
        if (StringUtils.isNotBlank(accessToken)) {
            deleteAccessToken(accessToken);
        }
    }

    @Override
    public void deleteAccessToken(String accessToken) {
        if (StringUtils.isNotBlank(accessToken)) {
            String token = accessTokenStore.get(accessToken);
            authenticationStore.remove(getTokenKey(token));
            accessTokenStore.remove(accessToken);
        }
    }

    @Override
    public void verifyAccessToken(LoginUser user) {
        long expireTime = user.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshAccessToken(user);
        }
    }

    @Override
    public void refreshAccessToken(LoginUser user) {
        user.setLoginTime(System.currentTimeMillis());
        user.setExpireTime(user.getLoginTime() + expireTime * MILLIS_MINUTE);
        //根据token缓存
        String tokenKey = getTokenKey(user.getToken());
        authenticationStore.put(tokenKey, user);
    }

    @Override
    public Map<String, LoginUser> getOnline() {
        Map<String, LoginUser> maps = new ConcurrentHashMap<>();
        if (accessTokenStore.size() > 0) {
            for (Map.Entry<String, String> entry : accessTokenStore.entrySet()) {
                String key = entry.getKey();
                maps.put(key, authenticationStore.get(getTokenKey(entry.getValue())));
            }
            return maps;
        }
        return null;
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
            token = token.replace(TOKEN_PREFIX, "");
        }
        return token;
    }

    private String getTokenKey(String uuid) {
        return LOGIN_TOKEN_KEY + uuid;
    }

    /**
     * <p>
     * 生成token
     * </p>
     *
     * @return token令牌
     */
    private String extractKey(String key) {
        Map<String, String> maps = Maps.newHashMap();
        maps.put("secret", secret);
        maps.put(LOGIN_USER_KEY, key);
        return createToken(maps);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param values 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, String> values) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(values.toString().getBytes(StandardCharsets.UTF_8));
            return String.format("%032x", new BigInteger(1, bytes));
        } catch (NoSuchAlgorithmException nsae) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).", nsae);
        }
    }
}
