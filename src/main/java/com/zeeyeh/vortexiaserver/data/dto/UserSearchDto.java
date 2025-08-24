package com.zeeyeh.vortexiaserver.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "用户搜索信息")
public class UserSearchDto {

    @Schema(title = "用户名", example = "qinloren")
    private String username;
    @Schema(title = "昵称", example = "清梦")
    private String nickname;
    @Schema(title = "邮箱", example = "qinloren@qq.com")
    private String email;
    @Schema(title = "状态", example = "1")
    private Integer status;
    @Schema(title = "角色Id", example = "1")
    private Long roleId;
    @Schema(title = "页码", example = "1")
    private Integer page;
    @Schema(title = "每页数量", example = "10")
    private Integer size;
}
