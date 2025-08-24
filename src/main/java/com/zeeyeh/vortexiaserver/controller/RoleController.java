package com.zeeyeh.vortexiaserver.controller;

import com.zeeyeh.vortexiaserver.data.dto.RoleCreateDto;
import com.zeeyeh.vortexiaserver.data.dto.RoleDeleteDto;
import com.zeeyeh.vortexiaserver.data.dto.RoleSearchDto;
import com.zeeyeh.vortexiaserver.data.dto.RoleUpdateDto;
import com.zeeyeh.vortexiaserver.data.vo.RoleSearchVo;
import com.zeeyeh.vortexiaserver.data.vo.RoleVo;
import com.zeeyeh.vortexiaserver.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    RoleService roleService;

    @PostMapping("create")
    @ResponseBody
    public RoleVo create(@RequestBody RoleCreateDto createDto) {
        return roleService.create(createDto);
    }

    @PostMapping("update")
    @ResponseBody
    public RoleVo update(@RequestBody RoleUpdateDto updateDto) {
        return roleService.update(updateDto);
    }

    @PostMapping("delete")
    @ResponseBody
    public void delete(@RequestBody RoleDeleteDto deleteDto) {
        roleService.delete(deleteDto);
    }

    @GetMapping("search")
    @ResponseBody
    public RoleSearchVo search(@RequestBody RoleSearchDto searchDto) {
        return roleService.search(searchDto);
    }
}
