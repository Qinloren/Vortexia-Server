package com.zeeyeh.vortexiaserver.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("role")
public class Role extends BaseEntity {
    @TableId(type = IdType.AUTO)
    @Schema(description = "角色Id", example = "1")
    private Long id;
    @Schema(description = "角色名", example = "admin")
    private String name;
    @Schema(description = "显示名", example = "管理员")
    private String displayName;
    @Schema(description = "描述", example = "具有最高系统管管理权")
    private String description;
}
