package com.zeeyeh.vortexiaserver.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("role")
public class Role extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String displayName;
    private String description;
    @TableLogic
    private Integer isDeleted;
}
