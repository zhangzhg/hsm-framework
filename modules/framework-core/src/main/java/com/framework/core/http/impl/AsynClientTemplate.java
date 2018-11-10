package com.framework.core.http.impl;


import com.framework.core.http.ClientTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

import java.net.URI;
import java.util.Map;

public class AsynClientTemplate extends AsyncRestTemplate implements ClientTemplate {
    private final AsyncRestTemplate asyncRestTemplate;

    public AsynClientTemplate(final AsyncRestTemplate asyncRestTemplate) {
        this.asyncRestTemplate = asyncRestTemplate;
    }

    @Override
    public <T> ResponseEntity<T> getObject(String url, Class<T> responseType) throws Exception {
        try {
            ListenableFuture<ResponseEntity<T>> t = asyncRestTemplate.getForEntity(url, responseType);
            return t.get();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public  <T> ResponseEntity<T> getObject(String url, ParameterizedTypeReference<T> responseType) throws Exception {
        try {
            ListenableFuture<ResponseEntity<T>> t = asyncRestTemplate.exchange(url, HttpMethod.GET, null, responseType);
            return t.get();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public <T> ResponseEntity<T> getObject(String url, Class<T> responseType, Map<String, ?> uriVariables) throws Exception {
        try {
            ListenableFuture<ResponseEntity<T>> t = asyncRestTemplate.exchange(url, HttpMethod.GET, null, responseType, uriVariables);
            return t.get();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public <T> ResponseEntity<T> getObject(String url, ParameterizedTypeReference<T> responseType, Map<String, ?> uriVariables) throws Exception {
        try {
            ListenableFuture<ResponseEntity<T>> t = asyncRestTemplate.exchange(url, HttpMethod.GET, null, responseType, uriVariables);
            return t.get();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public <T> ResponseEntity<T> postObject(String url, HttpEntity<?> request, Class<T> responseType, Object... uriVariables) throws Exception {
        try {
            ListenableFuture<ResponseEntity<T>> t = super.postForEntity(url, request, responseType, uriVariables);
            return t.get();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public <T> ResponseEntity<T> postObject(String url, HttpEntity<?> request, Class<T> responseType, Map<String, ?> uriVariables) throws Exception {
        try {
            ListenableFuture<ResponseEntity<T>> t = super.postForEntity(url, request, responseType, uriVariables);
            return t.get();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public  <T> ResponseEntity<T> postObject(URI url, HttpEntity<?> request, Class<T> responseType) throws Exception {
        try {
            ListenableFuture<ResponseEntity<T>> t = super.postForEntity(url, request, responseType);
            return t.get();
        } catch (Exception e) {
            throw e;
        }
    }

}
