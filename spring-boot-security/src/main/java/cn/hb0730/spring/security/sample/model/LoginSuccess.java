package cn.hb0730.spring.security.sample.model;

import org.springframework.security.core.userdetails.User;

import java.io.Serializable;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public class LoginSuccess implements Serializable {
    private String accessToken;

    private User user;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public User getLoginUser() {
        return user;
    }

    public void setLoginUser(User user) {
        this.user = user;
    }
}
