package com.hb0730.spring.boot.oauth2.server.error;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Component;

/**
 * <p>
 *     授权异常
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Component
public class WebResponseExceptionTranslator implements org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator<OAuth2Exception> {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {

        OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
        return ResponseEntity
                .status(oAuth2Exception.getHttpErrorCode())
                .body(new com.hb0730.spring.boot.oauth2.server.error.OAuth2Exception(oAuth2Exception.getMessage()));
    }
}
