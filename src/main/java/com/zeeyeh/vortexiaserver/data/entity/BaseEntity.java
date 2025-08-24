package com.zeeyeh.vortexiaserver.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseEntity implements Serializable {

    @Schema(description = "更新时间", example = "1755947949")
    private long updateTime;
    @Schema(description = "创建时间", example = "1755947949")
    private long createTime;
}
