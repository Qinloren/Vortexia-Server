package com.zeeyeh.vortexiaserver.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("permission")
public class Permission {
    @TableId(type = IdType.AUTO)
    @Schema(description = "权限Id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "权限名称", example = "user:create", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "权限描述", example = "创建用户", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(description = "权限分组", example = "user", requiredMode = Schema.RequiredMode.REQUIRED)
    private String group;
}
