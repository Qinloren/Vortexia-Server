package com.zeeyeh.vortexiaserver.data.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "用户信息")
public class UserVo {
    @Schema(title = "用户Id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long id;
    @Schema(title = "用户名", example = "qinloren", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    @Schema(title = "昵称", example = "清梦", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickname;
    @Schema(title = "邮箱", example = "qinloren@qq.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    @Schema(title = "头像", example = "https://qinloren.com/avatar.png")
    private String avatar;
    @Schema(title = "状态", example = "1")
    private int status;
    @Schema(title = "角色Id", example = "1")
    private long roleId;
    @Schema(title = "更新时间", example = "1755947949")
    private long updateTime;
    @Schema(title = "创建时间", example = "1755947949")
    private long createTime;
}
