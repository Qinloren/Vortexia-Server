package com.zeeyeh.vortexiaserver.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private int code;
    private String message;
    private T data;
    private Map<String, Object> headers;

    @NotNull
    @Contract("_ -> new")
    public static <T> Result<T> success(T data) {
        return any(0, "success", null, data);
    }

    @Contract("_, _ -> new")
    public static <T> @NotNull Result<T> success(T data, Map<String, Object> headers) {
        return any(0, "success", headers, data);
    }

    public static <T> @NotNull Result<T> any(int code, String message) {
        return new Result<>(code, message, null, Collections.emptyMap());
    }

    public static <T> @NotNull Result<T> any(int code, String message, Map<String, Object> headers) {
        return new Result<>(code, message, null, headers);
    }

    @Contract("_, _, _, _ -> new")
    public static <T> @NotNull Result<T> any(int code, String message, Map<String, Object> headers, T data) {
        return new Result<>(code, message, data, headers);
    }
}
