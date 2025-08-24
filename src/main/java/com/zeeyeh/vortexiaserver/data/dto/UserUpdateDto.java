package com.zeeyeh.vortexiaserver.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "用户更新信息")
public class UserUpdateDto {

    @Schema(title = "用户Id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(title = "用户名", example = "qinloren")
    private String username;

    @Schema(title = "昵称", example = "清梦")
    private String nickname;

    @Schema(title = "密码", example = "123456")
    private String password;

    @Schema(title = "邮箱", example = "qinloren@qq.com")
    private String email;

    @Schema(title = "头像", example = "https://qinloren.com/avatar.png")
    private String avatar;

    @Schema(title = "状态", example = "1")
    private Integer status;

    @Schema(title = "角色Id", example = "1")
    private Long roleId;
}
