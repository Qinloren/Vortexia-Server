package com.zeeyeh.vortexiaserver.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleCreateDto {
    private String name;
    private String displayName;
    private String description;
}
