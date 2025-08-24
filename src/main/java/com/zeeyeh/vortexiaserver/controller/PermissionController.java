package com.zeeyeh.vortexiaserver.controller;

import com.zeeyeh.vortexiaserver.data.dto.PermissionCreateDto;
import com.zeeyeh.vortexiaserver.data.dto.PermissionDeleteDto;
import com.zeeyeh.vortexiaserver.data.dto.PermissionSearchDto;
import com.zeeyeh.vortexiaserver.data.dto.PermissionUpdateDto;
import com.zeeyeh.vortexiaserver.data.vo.PermissionSearchVo;
import com.zeeyeh.vortexiaserver.data.vo.PermissionVo;
import com.zeeyeh.vortexiaserver.service.PermissionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Resource
    PermissionService permissionService;

    @PostMapping("create")
    @ResponseBody
    public PermissionVo create(@RequestBody  PermissionCreateDto createDto) {
        return permissionService.create(createDto);
    }

    @PostMapping("update")
    @ResponseBody
    public PermissionVo update(@RequestBody PermissionUpdateDto updateDto) {
        return permissionService.update(updateDto);
    }

    @PostMapping("delete")
    @ResponseBody
    public void delete(@RequestBody PermissionDeleteDto deleteDto) {
        permissionService.delete(deleteDto);
    }

    @GetMapping("search")
    @ResponseBody
    public PermissionSearchVo search(@RequestBody PermissionSearchDto searchDto) {
        return permissionService.search(searchDto);
    }
}
