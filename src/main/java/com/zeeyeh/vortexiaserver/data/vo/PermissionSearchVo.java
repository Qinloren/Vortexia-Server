package com.zeeyeh.vortexiaserver.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PermissionSearchVo {
    private List<PermissionVo> content;
    private Long total;
    private Integer number;
    private Integer size;
}
