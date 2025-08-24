package com.zeeyeh.vortexiaserver.service;

import com.zeeyeh.vortexiaserver.data.dto.RoleCreateDto;
import com.zeeyeh.vortexiaserver.data.dto.RoleDeleteDto;
import com.zeeyeh.vortexiaserver.data.dto.RoleSearchDto;
import com.zeeyeh.vortexiaserver.data.dto.RoleUpdateDto;
import com.zeeyeh.vortexiaserver.data.vo.RoleSearchVo;
import com.zeeyeh.vortexiaserver.data.vo.RoleVo;

public interface RoleService {
    /**
     * 创建角色
     * @param createDto 创建角色信息
     * @return 角色信息
     */
    RoleVo create(RoleCreateDto createDto);

    /**
     * 修改角色信息
     * @param updateDto 修改角色信息
     * @return 角色信息
     */
    RoleVo update(RoleUpdateDto updateDto);

    /**
     * 删除角色
     * @param deleteDto 删除角色信息
     */
    void delete(RoleDeleteDto deleteDto);

    /**
     * 角色搜索
     * @param searchDto 角色搜索信息
     * @return 角色搜索结果
     */
    RoleSearchVo search(RoleSearchDto searchDto);
}
