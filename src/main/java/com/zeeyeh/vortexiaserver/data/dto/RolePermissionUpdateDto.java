package com.zeeyeh.vortexiaserver.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionUpdateDto {
    private Long id;
    private Long roleId;
    private Long permissionId;
}
