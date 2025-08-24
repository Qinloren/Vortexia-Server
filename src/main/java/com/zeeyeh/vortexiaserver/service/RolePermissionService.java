package com.zeeyeh.vortexiaserver.service;

import com.zeeyeh.vortexiaserver.data.dto.RolePermissionCreateDto;
import com.zeeyeh.vortexiaserver.data.dto.RolePermissionDeleteDto;
import com.zeeyeh.vortexiaserver.data.dto.RolePermissionSearchDto;
import com.zeeyeh.vortexiaserver.data.dto.RolePermissionUpdateDto;
import com.zeeyeh.vortexiaserver.data.vo.RolePermissionSearchVo;
import com.zeeyeh.vortexiaserver.data.vo.RolePermissionVo;

public interface RolePermissionService {
    RolePermissionVo create(RolePermissionCreateDto createDto);
    RolePermissionVo update(RolePermissionUpdateDto updateDto);
    void delete(RolePermissionDeleteDto deleteDto);
    RolePermissionSearchVo search(RolePermissionSearchDto searchDto);
}
