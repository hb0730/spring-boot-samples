package com.hb0730.spring.boot.oauth2.server.error;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@JsonSerialize(using = OAuthExceptionJacksonSerializer.class)
public class OAuth2Exception extends org.springframework.security.oauth2.common.exceptions.OAuth2Exception {
    public OAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public OAuth2Exception(String msg) {
        super(msg);
    }
}
