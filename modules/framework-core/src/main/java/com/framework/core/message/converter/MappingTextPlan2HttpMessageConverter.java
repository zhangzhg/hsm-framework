package com.framework.core.message.converter;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * api 返回的content-type 如果是text/plan转换不过来
 * RestTemplate 增加处理text/plan类型的返回值
 */
public class MappingTextPlan2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
    public MappingTextPlan2HttpMessageConverter() {
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_PLAIN);
        mediaTypes.add(RestMediaType.TEXT_HTML_UTF8);
        setSupportedMediaTypes(mediaTypes);
    }
}
