package com.zeeyeh.vortexiaserver.data.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("permission")
public class Permission {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String description;
    @TableField("`group`")
    private String group;
    @TableLogic
    private Integer isDeleted;
}
