package com.zeeyeh.vortexiaserver.data.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(title = "用户搜索信息")
public class UserSearchVo {

    @Schema(title = "用户信息列表")
    private List<UserVo> content;

    @Schema(title = "用户信息总数")
    private Long total;

    @Schema(title = "当前页码")
    private Integer number;

    @Schema(title = "每页数量")
    private Integer size;

    @Schema(title = "总页数")
    private Integer pages;

    public UserSearchVo(List<UserVo> content, Long total, Integer number, Integer size) {
        this.content = content;
        this.total = total;
        this.number = number;
        this.size = size;
        this.pages = (int) Math.ceil((double) total / size);
    }
}
