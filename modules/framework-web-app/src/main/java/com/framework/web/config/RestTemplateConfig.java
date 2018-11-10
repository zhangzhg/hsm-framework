package com.framework.web.config;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.framework.core.http.impl.AsynClientTemplate;
import com.framework.core.message.converter.MappingTextPlan2HttpMessageConverter;
import com.framework.core.message.deSerializer.StringUnicodeDeSerializer;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfig {
    @Bean
    public AsynClientTemplate defaultAsynClientTemplate(AsyncRestTemplate asyncRestTemplate) {
        return new AsynClientTemplate(asyncRestTemplate);
    }

    @Bean
    public AsyncRestTemplate defaultAsyncRestTemplate() {
        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();
        MappingTextPlan2HttpMessageConverter converter = new MappingTextPlan2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new StringUnicodeDeSerializer());
        mapper.registerModule(module);
        converter.setObjectMapper(mapper);

        asyncRestTemplate.getMessageConverters().add(converter);
        return asyncRestTemplate;
    }

    @Bean
    public ClientHttpRequestFactory requestFactory() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return factory;
    }

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

        FormHttpMessageConverter formHttpMessageConverter= new FormHttpMessageConverter();
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        List<HttpMessageConverter<?>> partConverters = new ArrayList<>();

        partConverters.add(stringConverter);
        partConverters.add(new ResourceHttpMessageConverter());
        formHttpMessageConverter.setPartConverters(partConverters);

        restTemplate.getMessageConverters().add(formHttpMessageConverter);
        return restTemplate;
    }
}
