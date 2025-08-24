package com.zeeyeh.vortexiaserver.data.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BaseEntity implements Serializable {

    private LocalDateTime updateTime;
    private LocalDateTime createTime;
}
