package com.zeeyeh.vortexiaserver.controller.advice;

import com.alibaba.fastjson2.JSONObject;
import com.zeeyeh.vortexiaserver.data.entity.Result;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalResponseControllerAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.getParameterType().equals(ResponseEntity.class)
                && !returnType.getParameterType().equals(Result.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return new JSONObject()
                .fluentPut("code", 0)
                .fluentPut("message", "success")
                .fluentPut("timestamp", System.currentTimeMillis())
                .fluentPut("data", body);
    }
}
