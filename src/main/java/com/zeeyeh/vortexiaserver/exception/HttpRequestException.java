package com.zeeyeh.vortexiaserver.exception;

import com.zeeyeh.vortexiaserver.data.entity.ErrorResult;
import lombok.Getter;

@Getter
public class HttpRequestException extends RuntimeException {
    private final int code;

    public HttpRequestException(ErrorResult result) {
        this(result.getCode(), result.getMessage());
    }

    public HttpRequestException(int code, String message) {
        super(message);
        this.code = code;
    }
}
