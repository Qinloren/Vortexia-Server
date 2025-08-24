package com.zeeyeh.vortexiaserver.data.dto;

import lombok.Data;

@Data
public class UserSearchDto {

    private String username;
    private String nickname;
    private String email;
    private Integer status;
    private Long roleId;
    private Integer page;
    private Integer size;
}
