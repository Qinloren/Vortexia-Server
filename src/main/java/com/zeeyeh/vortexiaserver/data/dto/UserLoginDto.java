package com.zeeyeh.vortexiaserver.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "用户登录信息")
public class UserLoginDto {
    @Schema(title = "账号", description = "支持:用户Id/用户名/邮箱", example = "1/qinloren/qinloren@qq.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String account;
    @Schema(title = "密码", example = "123456", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
