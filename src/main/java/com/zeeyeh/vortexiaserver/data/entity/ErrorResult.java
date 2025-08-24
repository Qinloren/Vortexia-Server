package com.zeeyeh.vortexiaserver.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorResult {
    USER_NOT_LOGIN(1000, "用户未登录"),
    USERNAME_ALREADY_EXIST(1001, "用户名已存在"),
    USER_NOT_FOUND(1002, "用户不存在"),
    INVALID_USER_INFO(1003, "无效的用户信息"),
    PASSWORD_ERROR(1004, "密码错误"),
    USER_ALREADY_EXIST(1005, "用户已存在"),
    USER_CREATE_FAILED(1006, "用户创建失败"),
    ROLE_ALREADY_EXIST(1100, "角色已存在"),
    ROLE_NOT_FOUND(1101, "角色不存在"),
    ROLE_CREATE_FAILED(1102, "角色创建失败"),
    PERMISSION_ALREADY_EXIST(1200, "权限已存在"),
    PERMISSION_NOT_FOUND(1201, "权限不存在"),
    PERMISSION_CREATE_FAILED(1202, "权限创建失败"),
    ;
    private final int code;
    private final String message;
}
