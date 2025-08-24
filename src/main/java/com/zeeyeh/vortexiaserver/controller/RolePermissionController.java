package com.zeeyeh.vortexiaserver.controller;

import com.zeeyeh.vortexiaserver.data.dto.RolePermissionCreateDto;
import com.zeeyeh.vortexiaserver.data.dto.RolePermissionDeleteDto;
import com.zeeyeh.vortexiaserver.data.dto.RolePermissionSearchDto;
import com.zeeyeh.vortexiaserver.data.dto.RolePermissionUpdateDto;
import com.zeeyeh.vortexiaserver.data.vo.RolePermissionSearchVo;
import com.zeeyeh.vortexiaserver.data.vo.RolePermissionVo;
import com.zeeyeh.vortexiaserver.service.RolePermissionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role/permission")
public class RolePermissionController {

    @Resource
    RolePermissionService rolePermissionService;

    @PostMapping("create")
    @ResponseBody
    public RolePermissionVo create(@RequestBody RolePermissionCreateDto createDto) {
        return rolePermissionService.create(createDto);
    }

    @PostMapping("update")
    @ResponseBody
    public RolePermissionVo update(@RequestBody RolePermissionUpdateDto updateDto) {
        return rolePermissionService.update(updateDto);
    }

    @PostMapping("delete")
    @ResponseBody
    public void delete(@RequestBody RolePermissionDeleteDto deleteDto) {
        rolePermissionService.delete(deleteDto);
    }

    @GetMapping("search")
    @ResponseBody
    public RolePermissionSearchVo search(@RequestBody RolePermissionSearchDto searchDto) {
        return rolePermissionService.search(searchDto);
    }
}
