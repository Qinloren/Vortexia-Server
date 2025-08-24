package com.zeeyeh.vortexiaserver.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {
    private long id;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String avatar;
    private int status;
    private long roleId;
}
