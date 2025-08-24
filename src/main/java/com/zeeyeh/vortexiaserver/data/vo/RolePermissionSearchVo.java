package com.zeeyeh.vortexiaserver.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionSearchVo {
    private List<RolePermissionVo> content;
    private Long total;
    private Integer number;
    private Integer size;
}
