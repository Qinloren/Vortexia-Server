package com.zeeyeh.vortexiaserver.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    private long id;
    private String username;
    private String nickname;
    private String email;
    private String avatar;
    private int status;
    private long roleId;
    private long updateTime;
    private long createTime;
}
