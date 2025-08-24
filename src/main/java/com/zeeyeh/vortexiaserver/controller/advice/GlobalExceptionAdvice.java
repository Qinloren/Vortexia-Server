package com.zeeyeh.vortexiaserver.controller.advice;

import com.zeeyeh.vortexiaserver.data.entity.Result;
import com.zeeyeh.vortexiaserver.exception.HttpRequestException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(HttpRequestException.class)
    public Result<?> handleHttpRequestException(HttpRequestException e) {
        return Result.any(e.getCode(), e.getMessage());
    }
}
