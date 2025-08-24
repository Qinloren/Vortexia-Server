package com.zeeyeh.vortexiaserver.data.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    NORMAL(0, "正常"),
    VERIFYING(1, "待验证"),
    LOCKED(2, "锁定");

    @EnumValue
    private final int code;
    private final String message;

    public static UserStatus from(int code) {
        for (UserStatus value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        return null;
    }
}
