package com.zeeyeh.vortexiaserver.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleSearchDto {
    private String name;
    private String displayName;
    private String description;
    private Integer page;
    private Integer size;
}
