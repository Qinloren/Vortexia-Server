package com.zeeyeh.vortexiaserver.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo {
    private Long id;
    private String name;
    private String displayName;
    private String description;
}
