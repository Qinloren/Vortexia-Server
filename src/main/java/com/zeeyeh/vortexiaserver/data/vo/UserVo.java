package com.zeeyeh.vortexiaserver.data.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zeeyeh.vortexiaserver.data.codec.serializable.LocalDateTimeSerializable;
import com.zeeyeh.vortexiaserver.data.codec.serializable.UserStatusSerializable;
import com.zeeyeh.vortexiaserver.data.entity.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String avatar;
    @JsonSerialize(using = UserStatusSerializable.class)
    private UserStatus status;
    private Long roleId;
    @JsonSerialize(using = LocalDateTimeSerializable.class)
    private LocalDateTime updateTime;
    @JsonSerialize(using = LocalDateTimeSerializable.class)
    private LocalDateTime createTime;
}
