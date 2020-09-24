package com.hb0730.spring.boot.oauth2.server.error;

import com.hb0730.spring.boot.oauth2.server.model.CodeMessage;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Map;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public class OAuthExceptionJacksonSerializer extends StdSerializer<OAuth2Exception> {

    public OAuthExceptionJacksonSerializer() {
        super(OAuth2Exception.class);
    }

    @Override
    public void serialize(OAuth2Exception value, JsonGenerator jgen, SerializerProvider serializerProvider) throws
            IOException {
        jgen.writeStartObject();
        jgen.writeObjectField("code", CodeMessage.LOGIN_FAILURE_CODE);
        jgen.writeStringField("msg", value.getMessage());
        if (value.getAdditionalInformation()!=null) {
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                jgen.writeStringField(key, add);
            }
        }
        jgen.writeEndObject();
    }
}
