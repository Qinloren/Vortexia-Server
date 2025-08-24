package com.zeeyeh.vortexiaserver.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionVo {
    private Long id;
    private Long roleId;
    private Long permissionId;
}
