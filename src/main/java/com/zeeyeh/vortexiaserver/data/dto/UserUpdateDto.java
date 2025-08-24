package com.zeeyeh.vortexiaserver.data.dto;

import lombok.Data;

@Data
public class UserUpdateDto {
    private Long id;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String avatar;
    private Integer status;
    private Long roleId;
}
