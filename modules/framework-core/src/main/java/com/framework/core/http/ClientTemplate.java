package com.framework.core.http;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.RestClientException;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.Future;

public interface ClientTemplate {
    <T> ResponseEntity<T> getObject(String url, Class<T> responseType) throws Exception;

    <T> ResponseEntity<T> getObject(String url, ParameterizedTypeReference<T> responseType) throws Exception;

    <T> ResponseEntity<T> getObject(String url, Class<T> responseType, Map<String, ?> uriVariables) throws Exception;

    <T> ResponseEntity<T> getObject(String url, ParameterizedTypeReference<T> responseType, Map<String, ?> uriVariables) throws Exception;

    <T> ResponseEntity<T> postObject(String url, HttpEntity<?> request, Class<T> responseType, Object... uriVariables) throws Exception;

    <T> ResponseEntity<T> postObject(String url, HttpEntity<?> request, Class<T> responseType, Map<String, ?> uriVariables) throws Exception;

    <T> ResponseEntity<T> postObject(URI url, HttpEntity<?> request, Class<T> responseType) throws Exception;
}
