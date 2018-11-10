package com.framework.core.message.converter;

import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.util.Map;

public class RestMediaType extends MediaType {
    public final static String TEXT_HTML_UTF8_VALUE = "text/html;charset=UTF-8";
    public final static MediaType TEXT_HTML_UTF8;

    static {
        TEXT_HTML_UTF8 = valueOf(TEXT_HTML_UTF8_VALUE);
    }

    public RestMediaType(String type) {
        super(type);
    }

    public RestMediaType(String type, String subtype) {
        super(type, subtype);
    }

    public RestMediaType(String type, String subtype, Charset charset) {
        super(type, subtype, charset);
    }

    public RestMediaType(String type, String subtype, double qualityValue) {
        super(type, subtype, qualityValue);
    }

    public RestMediaType(MediaType other, Charset charset) {
        super(other, charset);
    }

    public RestMediaType(MediaType other, Map<String, String> parameters) {
        super(other, parameters);
    }

    public RestMediaType(String type, String subtype, Map<String, String> parameters) {
        super(type, subtype, parameters);
    }
}
