package com.zeeyeh.vortexiaserver.data.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserSearchVo {

    private List<UserVo> content;
    private Long total;
    private Integer number;
    private Integer size;

    public UserSearchVo(List<UserVo> content, Long total, Integer number, Integer size) {
        this.content = content;
        this.total = total;
        this.number = number;
        this.size = size;
    }
}
