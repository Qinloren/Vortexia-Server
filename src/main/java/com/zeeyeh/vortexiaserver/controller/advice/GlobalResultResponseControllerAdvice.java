package com.zeeyeh.vortexiaserver.controller.advice;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.zeeyeh.vortexiaserver.data.entity.Result;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalResultResponseControllerAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getParameterType().equals(Result.class);
    }

    @Override
    public Object beforeBodyWrite(Object bodyObject, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (bodyObject instanceof Result<?> body) {
            Map<String, Object> headers = body.getHeaders();
            if (headers != null) {
                for (Map.Entry<String, Object> entry : headers.entrySet()) {
                    response.getHeaders().add(entry.getKey(), entry.getValue().toString());
                }
            }
            return new JSONObject()
                    .fluentPut("code", body.getCode())
                    .fluentPut("message", body.getMessage())
                    .fluentPut("timestamp", System.currentTimeMillis())
                    .fluentPut("data", body.getData());
        }
        return bodyObject;
    }
}
