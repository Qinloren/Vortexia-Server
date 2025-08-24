package com.zeeyeh.vortexiaserver.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "用户删除信息")
public class UserDeleteDto {

    @Schema(title = "用户Id列表")
    private Long id;
}
