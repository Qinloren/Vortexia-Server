package com.zeeyeh.vortexiaserver.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("user")
public class User extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private long id;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String avatar;
    private UserStatus status;
    private Role role;
    @TableLogic
    private Integer isDeleted;
}
