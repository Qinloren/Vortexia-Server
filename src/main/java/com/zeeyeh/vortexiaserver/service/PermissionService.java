package com.zeeyeh.vortexiaserver.service;

import com.zeeyeh.vortexiaserver.data.dto.PermissionCreateDto;
import com.zeeyeh.vortexiaserver.data.dto.PermissionDeleteDto;
import com.zeeyeh.vortexiaserver.data.dto.PermissionSearchDto;
import com.zeeyeh.vortexiaserver.data.dto.PermissionUpdateDto;
import com.zeeyeh.vortexiaserver.data.vo.PermissionSearchVo;
import com.zeeyeh.vortexiaserver.data.vo.PermissionVo;

public interface PermissionService {

    /**
     * 创建权限
     * @param createDto 创建权限参数
     * @return 权限信息
     */
    PermissionVo create(PermissionCreateDto createDto);

    /**
     * 修改权限
     * @param updateDto 修改权限参数
     * @return 权限信息
     */
    PermissionVo update(PermissionUpdateDto updateDto);

    /**
     * 删除权限
     * @param deleteDto 删除权限参数
     */
    void delete(PermissionDeleteDto deleteDto);

    /**
     * 查询权限
     * @param searchDto 查询权限参数
     * @return 权限信息
     */
    PermissionSearchVo search(PermissionSearchDto searchDto);
}
