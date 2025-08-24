package com.zeeyeh.vortexiaserver.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("role_permission")
public class RolePermission extends BaseEntity {
    @TableId(type = IdType.AUTO)
    @Schema(description = "权限Id", example = "1")
    private Long id;

    @Schema(description = "角色Id", example = "1")
    private Long roleId;

    @Schema(description = "权限Id", example = "1")
    private Long permissionId;
}
